
package at.rewe;

import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/api/excel")
public class ExcelController {

    @Autowired
    private ExcelReader excelReader;
    //curl -X POST http://localhost:8080/riag/supply/article-mdm/tgm-x2fresh/backend/api/excel/uploadFile \ -F "file=@/Users/TGM_x2FRESH/Documents/Stammdatenblätter/ARTIKEL-STAMMBLATT (1).xlsx"
    @PostMapping("/uploadFile")
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) {
            Map<String, CellReference> map = new HashMap<String, CellReference>();
            map.put("Lieferant Bezeichnung", new CellReference("B7"));
            map.put("GLN", new CellReference("B9"));
            map.put("Ware lieferbar ab", new CellReference("V9"));
            map.put("Artikelbeschreibung", new CellReference("Q13"));
            map.put("Marke", new CellReference("I15"));
            // Call the ExcelReader to read the values

            Map<String,String> cellValues = excelReader.readCellsFromUpload(file, map);
            System.out.println(cellValues.toString());
            return ResponseEntity.ok(cellValues.toString());
    }
}
