package at.rewe;

import at.rewe.definition.DefinitionApi;
import java.util.Map;
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

  //curl -X POST http://localhost:8080/riag/supply/article-mdm/tgm-x2fresh/backend/api/excel/uploadFile \ -F "file=@/Users/TGM_x2FRESH/Documents/Stammdatenblätter/ARTIKEL-STAMMBLATT (1).xlsx"
  @PostMapping("/uploadFile")
  public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) {
    definitionApi.getDefinitions("stammblatt");
    Map<String, CellReference> map = new TreeMap<String, CellReference>();
    map.put("Lieferant Bezeichnung", new CellReference("B7"));
    map.put("GLN", new CellReference("B9"));
    map.put("Ware lieferbar ab", new CellReference("V9"));
    map.put("Artikelbeschreibung", new CellReference("Q13"));
    map.put("Marke", new CellReference("I15"));
    // Call the ExcelReader to read the values

    Map<String, String> cellValues = excelReader.readCellsFromUpload(file, map);
    Validation validation = new Validation(cellValues);
    validation.validate();
    System.out.println(cellValues.toString());
    return ResponseEntity.ok(cellValues.toString());
  }
}
