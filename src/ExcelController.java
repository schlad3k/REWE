package at.rewe;

import at.rewe.definition.DefinitionApi;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/excel")
public class ExcelController {

  @Autowired
  private ExcelReader excelReader;

  @Autowired
  private DefinitionApi definitionApi;

  @PostMapping("/uploadFile")
  public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) {
    definitionApi.getDefinitions("stammblatt");
    TreeMap<String, CellReference> map = new TreeMap<>();

    // 1. Lieferant (Supplier)
    CellReference lieferantBezeichnung = new CellReference("B7");
    lieferantBezeichnung.setRegex("^.{1,70}$");  // Max 70 characters
    map.put("Lieferant Bezeichnung", lieferantBezeichnung);

    CellReference gln = new CellReference("B9");
    gln.setRegex("^\\d{13}$");  // 13-digit numeric value
    map.put("GLN", gln);

    CellReference wareLieferbarAb = new CellReference("V9");
    wareLieferbarAb.setRegex("^(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[0-2])\\.\\d{4}$");  // Date format TT.MM.YYYY
    map.put("Ware lieferbar ab", wareLieferbarAb);

    CellReference lieferantArtikelnr = new CellReference("AS7");
    lieferantArtikelnr.setRegex("^\\d+$");  // Numeric
    map.put("Lieferant Artikelnr", lieferantArtikelnr);

    CellReference ausgefuelltVon = new CellReference("AS9");
    ausgefuelltVon.setRegex(".*");  // Any value
    map.put("Ausgefüllt von", ausgefuelltVon);

    // 2. Artikel (Item)
    CellReference artikelBeschreibung = new CellReference("Q13");
    artikelBeschreibung.setRegex("^.{1,40}$");  // Max 40 characters
    map.put("Artikelbeschreibung", artikelBeschreibung);

    CellReference marke = new CellReference("I15");
    marke.setRegex(".*");  // Any value
    map.put("Marke", marke);

    // 3. Steuern (Taxes)
    CellReference getraenkeStr = new CellReference("L19");
    getraenkeStr.setRegex("^\\d+$");  // Numeric
    map.put("Getränkesteuern", getraenkeStr);

    CellReference sonstigeStr = new CellReference("T19");
    sonstigeStr.setRegex(".*");  // Any value
    map.put("Sonstige Steuern", sonstigeStr);

    // 4. Verpackung (Packaging)
    CellReference massmenge = new CellReference("T23");
    massmenge.setRegex("^\\d+(\\.\\d{1,4})?$");  // Numeric with max 4 decimals
    map.put("Maßmenge", massmenge);

    // 5. Mindesthaltbarkeit (Minimum Shelf Life)
    CellReference mhInTagen = new CellReference("B27");
    mhInTagen.setRegex("^\\d+$");  // Numeric
    map.put("MH in Tagen", mhInTagen);

    CellReference restlaufzeit = new CellReference("V27");  // Specify the correct cell
    restlaufzeit.setRegex("^\\d+$");  // Numeric
    map.put("Restlaufzeit in Tagen", restlaufzeit);

    // 6. Produktion (Production)
    CellReference eanE = new CellReference("B35");  // Specify correct cell
    eanE.setRegex("^\\d+$");  // Numeric
    map.put("Zolltarifnummer (EAN E)", eanE);

    // 7. GTIN Nummern (GTIN Numbers)
    CellReference vGTIN = new CellReference("AB38");
    vGTIN.setRegex("^\\d{1,14}$");  // Max 14 digits, numeric
    map.put("V-GTIN", vGTIN);

    CellReference bGTIN = new CellReference("AB39");
    bGTIN.setRegex("^\\d{1,14}$");  // Max 14 digits, numeric
    map.put("B-GTIN", bGTIN);

    CellReference tGTIN = new CellReference("AB40");
    tGTIN.setRegex("^\\d{1,14}$");  // Max 14 digits, numeric
    map.put("T-GTIN", tGTIN);

    CellReference g6 = new CellReference("AB41");
    g6.setRegex("^\\d{1,14}$");  // Max 14 digits, numeric
    map.put("GTIN für Datenübertragung (6)", g6);

    // 8. Verpackung (Packaging)
    CellReference veInBe = new CellReference("AE44");
    veInBe.setRegex("^\\d+$");  // Numeric
    map.put("VE in BE", veInBe);

    CellReference beInPalette = new CellReference("AE45");
    beInPalette.setRegex("^\\d+$");  // Numeric
    map.put("BE in Palette", beInPalette);

    CellReference beInLage = new CellReference("AE46");
    beInLage.setRegex("^\\d+$");  // Numeric
    map.put("BE in Lage", beInLage);

    // 9. Abmessung (Dimensions)
    CellReference höheVE = new CellReference("O52");
    höheVE.setRegex("^\\d+(\\.\\d{1,4})?$");  // Numeric with max 4 decimals
    map.put("Höhe VE", höheVE);

    CellReference breiteVE = new CellReference("O53");
    breiteVE.setRegex("^\\d+(\\.\\d{1,4})?$");  // Numeric with max 4 decimals
    map.put("Breite VE", breiteVE);

    CellReference tiefeVE = new CellReference("O54");
    tiefeVE.setRegex("^\\d+(\\.\\d{1,4})?$");  // Numeric with max 4 decimals
    map.put("Tiefe VE", tiefeVE);

    CellReference gewichtTaraVE = new CellReference("O55");
    gewichtTaraVE.setRegex("^\\d+(\\.\\d{1,4})?$");  // Numeric with max 4 decimals
    map.put("Gewicht inkl. Tara VE", gewichtTaraVE);

    CellReference taraVE = new CellReference("O56");
    taraVE.setRegex("^\\d+(\\.\\d{1,4})?$");  // Numeric with max 4 decimals
    map.put("Tara VE", taraVE);

    CellReference abtropfGewichtVE = new CellReference("O57");
    abtropfGewichtVE.setRegex("^\\d+(\\.\\d{1,4})?$");  // Numeric with max 4 decimals
    map.put("Abtropfgewicht VE", abtropfGewichtVE);

    CellReference nettofüllGewichtVE = new CellReference("O58");
    nettofüllGewichtVE.setRegex("^\\d+(\\.\\d{1,4})?$");  // Numeric with max 4 decimals
    map.put("Nettofüllgewicht VE", nettofüllGewichtVE);

    CellReference höheBE = new CellReference("X52");
    höheBE.setRegex("^\\d+(\\.\\d{1,4})?$");  // Numeric with max 4 decimals
    map.put("Höhe BE", höheBE);

    CellReference breiteBE = new CellReference("X53");
    breiteBE.setRegex("^\\d+(\\.\\d{1,4})?$");  // Numeric with max 4 decimals
    map.put("Breite BE", breiteBE);

    CellReference tiefeBE = new CellReference("X54");
    tiefeBE.setRegex("^\\d+(\\.\\d{1,4})?$");  // Numeric with max 4 decimals
    map.put("Tiefe BE", tiefeBE);

    CellReference gewichtTaraBE = new CellReference("X55");
    gewichtTaraBE.setRegex("^\\d+(\\.\\d{1,4})?$");  // Numeric with max 4 decimals
    map.put("Gewicht inkl. Tara BE", gewichtTaraBE);

    CellReference taraBE = new CellReference("X56");
    taraBE.setRegex("^\\d+(\\.\\d{1,4})?$");  // Numeric with max 4 decimals
    map.put("Tara BE", taraBE);

    CellReference höhePalette = new CellReference("AG52");
    höhePalette.setRegex("^\\d+(\\.\\d{1,4})?$");  // Numeric with max 4 decimals
    map.put("Höhe Palette", höhePalette);

    CellReference gewichtTaraPalette = new CellReference("AG55");
    gewichtTaraPalette.setRegex("^\\d+(\\.\\d{1,4})?$");  // Numeric with max 4 decimals
    map.put("Gewicht inkl. Tara Palette", gewichtTaraPalette);

    // 10. Lagerung (Storage)
    CellReference maxTemp = new CellReference("AS13");
    maxTemp.setRegex("^\\d+$");  // Numeric
    map.put("Max Temp. bei Lagerung", maxTemp);

    CellReference gefahrengutTrue = new CellReference("BP15");
    gefahrengutTrue.setRegex("^[xXjJyY]$");  // Checkbox
    map.put("Gefahrgut True", gefahrengutTrue);

    CellReference gefahrengutFalse = new CellReference("BW15");
    gefahrengutFalse.setRegex("^[xXjJyY]$");  // Checkbox
    map.put("Gefahrgut False", gefahrengutFalse);

    CellReference flammableTrue = new CellReference("BZ13");
    flammableTrue.setRegex("^[xXjJyY]$");  // Checkbox
    map.put("Flammable True", flammableTrue);

    CellReference flammableFalse = new CellReference("BZ14");
    flammableFalse.setRegex("^[xXjJyY]$");  // Checkbox
    map.put("Flammable False", flammableFalse);

    // 11. Verpflichtende Angabe Artikelmerkmale (Mandatory Article Features)
    CellReference keines = new CellReference("AT21");
    keines.setRegex("^[xXjJyY]$");  // Checkbox
    map.put("keines der angeführten Artikelmerkmale ist zutreffend", keines);

    // Labels/Gütesiegel (Certification labels)
    CellReference bioAustriaJa = new CellReference("AS25");
    bioAustriaJa.setRegex("^[xXjJyY]$");  // Checkbox für "Ja" bei "Bio Austria"
    map.put("Bio Austria Ja", bioAustriaJa);

    CellReference bioAustriaNein = new CellReference("AV25");
    bioAustriaNein.setRegex("^[xXjJyY]$");  // Checkbox für "Nein" bei "Bio Austria"
    map.put("Bio Austria Nein", bioAustriaNein);

    CellReference amaGutesiegelJa = new CellReference("AS26");
    amaGutesiegelJa.setRegex("^[xXjJyY]$");  // Checkbox für "Ja" bei "AMA Gütesiegel"
    map.put("AMA Gütesiegel Ja", amaGutesiegelJa);

    CellReference amaGutesiegelNein = new CellReference("AV26");
    amaGutesiegelNein.setRegex("^[xXjJyY]$");  // Checkbox für "Nein" bei "AMA Gütesiegel"
    map.put("AMA Gütesiegel Nein", amaGutesiegelNein);

    CellReference ascJa = new CellReference("AS27");
    ascJa.setRegex("^[xXjJyY]$");  // Checkbox für "Ja" bei "ASC"
    map.put("ASC Ja", ascJa);

    CellReference ascNein = new CellReference("AV27");
    ascNein.setRegex("^[xXjJyY]$");  // Checkbox für "Nein" bei "ASC"
    map.put("ASC Nein", ascNein);

    CellReference euBioJa = new CellReference("AS28");
    euBioJa.setRegex("^[xXjJyY]$");  // Checkbox für "Ja" bei "EU-BIO"
    map.put("EU-BIO Ja", euBioJa);

    CellReference euBioNein = new CellReference("AV28");
    euBioNein.setRegex("^[xXjJyY]$");  // Checkbox für "Nein" bei "EU-BIO"
    map.put("EU-BIO Nein", euBioNein);

    CellReference globalGapZertJa = new CellReference("AS29");
    globalGapZertJa.setRegex("^[xXjJyY]$");  // Checkbox für "Ja" bei "Global GAP zert."
    map.put("Global GAP zert. Ja", globalGapZertJa);

    CellReference globalGapZertNein = new CellReference("AV29");
    globalGapZertNein.setRegex("^[xXjJyY]$");  // Checkbox für "Nein" bei "Global GAP zert."
    map.put("Global GAP zert. Nein", globalGapZertNein);

    CellReference gentechnikFreiJa = new CellReference("AS30");
    gentechnikFreiJa.setRegex("^[xXjJyY]$");  // Checkbox für "Ja" bei "Gentechnik frei"
    map.put("Gentechnik frei Ja", gentechnikFreiJa);

    CellReference gentechnikFreiNein = new CellReference("AV30");
    gentechnikFreiNein.setRegex("^[xXjJyY]$");  // Checkbox für "Nein" bei "Gentechnik frei"
    map.put("Gentechnik frei Nein", gentechnikFreiNein);

    CellReference mscZertJa = new CellReference("AS31");
    mscZertJa.setRegex("^[xXjJyY]$");  // Checkbox für "Ja" bei "MSC zert."
    map.put("MSC zert. Ja", mscZertJa);

    CellReference mscZertNein = new CellReference("AV31");
    mscZertNein.setRegex("^[xXjJyY]$");  // Checkbox für "Nein" bei "MSC zert."
    map.put("MSC zert. Nein", mscZertNein);

    CellReference vLabelVeganJa = new CellReference("AS32");
    vLabelVeganJa.setRegex("^[xXjJyY]$");  // Checkbox für "Ja" bei "V-Label vegan"
    map.put("V-Label vegan Ja", vLabelVeganJa);

    CellReference vLabelVeganNein = new CellReference("AV32");
    vLabelVeganNein.setRegex("^[xXjJyY]$");  // Checkbox für "Nein" bei "V-Label vegan"
    map.put("V-Label vegan Nein", vLabelVeganNein);

    CellReference vLabelVegetarischJa = new CellReference("AS33");
    vLabelVegetarischJa.setRegex("^[xXjJyY]$");  // Checkbox für "Ja" bei "V-Label vegetarisch"
    map.put("V-Label vegetarisch Ja", vLabelVegetarischJa);

    CellReference vLabelVegetarischNein = new CellReference("AV33");
    vLabelVegetarischNein.setRegex("^[xXjJyY]$");  // Checkbox für "Nein" bei "V-Label vegetarisch"
    map.put("V-Label vegetarisch Nein", vLabelVegetarischNein);

    CellReference vLabelVonNaturAusVeganJa = new CellReference("AS34");
    vLabelVonNaturAusVeganJa.setRegex("^[xXjJyY]$");  // Checkbox für "Ja" bei "V-Label von Natur aus vegan"
    map.put("V-Label von Natur aus vegan Ja", vLabelVonNaturAusVeganJa);

    CellReference vLabelVonNaturAusVeganNein = new CellReference("AV34");
    vLabelVonNaturAusVeganNein.setRegex("^[xXjJyY]$");  // Checkbox für "Nein" bei "V-Label von Natur aus vegan"
    map.put("V-Label von Natur aus vegan Nein", vLabelVonNaturAusVeganNein);

    //11 MERKMALE
    CellReference fairZumTierJa = new CellReference("BR25");
    fairZumTierJa.setRegex("^[xXjJyY]$");  // Checkbox für "Ja" bei "Fair zum Tier"
    map.put("Fair zum Tier Ja", fairZumTierJa);

    CellReference fairZumTierNein = new CellReference("BU25");
    fairZumTierNein.setRegex("^[xXjJyY]$");  // Checkbox für "Nein" bei "Fair zum Tier"
    map.put("Fair zum Tier Nein", fairZumTierNein);

    CellReference glutenfreiJa = new CellReference("BR26");
    glutenfreiJa.setRegex("^[xXjJyY]$");  // Checkbox für "Ja" bei "glutenfrei"
    map.put("glutenfrei Ja", glutenfreiJa);

    CellReference glutenfreiNein = new CellReference("BU26");
    glutenfreiNein.setRegex("^[xXjJyY]$");  // Checkbox für "Nein" bei "glutenfrei"
    map.put("glutenfrei Nein", glutenfreiNein);

    CellReference laktosefreiJa = new CellReference("BR27");
    laktosefreiJa.setRegex("^[xXjJyY]$");  // Checkbox für "Ja" bei "laktosefrei"
    map.put("laktosefrei Ja", laktosefreiJa);

    CellReference laktosefreiNein = new CellReference("BU27");
    laktosefreiNein.setRegex("^[xXjJyY]$");  // Checkbox für "Nein" bei "laktosefrei"
    map.put("laktosefrei Nein", laktosefreiNein);

    CellReference vegetarischJa = new CellReference("BR28");
    vegetarischJa.setRegex("^[xXjJyY]$");  // Checkbox für "Ja" bei "vegetarisch"
    map.put("vegetarisch Ja", vegetarischJa);

    CellReference vegetarischNein = new CellReference("BU28");
    vegetarischNein.setRegex("^[xXjJyY]$");  // Checkbox für "Nein" bei "vegetarisch"
    map.put("vegetarisch Nein", vegetarischNein);

    CellReference veganJa = new CellReference("BR29");
    veganJa.setRegex("^[xXjJyY]$");  // Checkbox für "Ja" bei "vegan"
    map.put("vegan Ja", veganJa);

    CellReference veganNein = new CellReference("BU29");
    veganNein.setRegex("^[xXjJyY]$");  // Checkbox für "Nein" bei "vegan"
    map.put("vegan Nein", veganNein);

    CellReference vonNaturAusVeganJa = new CellReference("BR30");
    vonNaturAusVeganJa.setRegex("^[xXjJyY]$");  // Checkbox für "Ja" bei "von Natur aus vegan"
    map.put("von Natur aus vegan Ja", vonNaturAusVeganJa);

    CellReference vonNaturAusVeganNein = new CellReference("BU30");
    vonNaturAusVeganNein.setRegex("^[xXjJyY]$");  // Checkbox für "Nein" bei "von Natur aus vegan"
    map.put("von Natur aus vegan Nein", vonNaturAusVeganNein);

    // 12. Verpackungsentpflichtungen (Packaging Obligations)
    CellReference entpflichtet = new CellReference("");
    entpflichtet.setRegex("^[xXjJyY]$");  // Checkbox
    map.put("Entpflichtet?", entpflichtet);

    // 13. Einkaufspreis (Purchase Price)
    CellReference brutto = new CellReference("Brutto_Cell");
    brutto.setRegex("^\\d+(\\.\\d{1,4})?$");  // Numeric with max 4 decimals
    map.put("Brutto", brutto);

    CellReference faktura = new CellReference("Faktura_Cell");
    faktura.setRegex("^\\d+(\\.\\d{1,4})?$");  // Numeric with max 4 decimals
    map.put("Faktura", faktura);

    CellReference netto = new CellReference("Netto_Cell");
    netto.setRegex("^\\d+(\\.\\d{1,4})?$");  // Numeric with max 4 decimals
    map.put("Netto", netto);

    // 14. Elektronische Stammdaten (Electronic Master Data)
    CellReference zuständigePerson = new CellReference("ZustaendigePerson_Cell");
    zuständigePerson.setRegex("^[xXjJyY]$");  // Checkbox
    map.put("Zuständige Person", zuständigePerson);

    // Use TreeMap to maintain sorted order
    TreeMap<String, String> cellValues = excelReader.readCellsFromUpload(file, map);
    Validation validation = new Validation(cellValues);
    System.out.println(validation.validate());
    System.out.println(cellValues.toString());
    return ResponseEntity.ok(cellValues.toString());
  }
}
