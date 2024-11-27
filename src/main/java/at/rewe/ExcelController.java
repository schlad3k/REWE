
package at.rewe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/excel")
public class ExcelController {

    @Autowired
    private ExcelReader excelReader;
//curl -X POST http://localhost:8080/riag/supply/article-mdm/tgm-x2fresh/backend/api/excel/uploadFile \ -F "file=@/Users/TGM_x2FRESH/Documents/Stammdatenblätter/ARTIKEL-STAMMBLATT (1).xlsx"
    @PostMapping("/uploadFile")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
            List<String> list = new ArrayList<>();
            list.add("I15");
            list.add("BQ54");
            // Call the ExcelReader to read the values
            List<String> cellValues = excelReader.readCellsFromUpload(file, list);

            return ResponseEntity.ok(cellValues.toString());


    }
    /*
    @PostMapping("/upload")
    public ResponseEntity<List<String>> uploadExcelFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("cellReferences") String cellReferences) {
        try {
            // Split the cell references into a list
            List<String> cellReferenceList = Arrays.asList(cellReferences.split("Q13,"));

            // Call the ExcelReader to read the values
            List<String> cellValues = excelReader.readCellsFromUpload(file, cellReferenceList);

            return ResponseEntity.ok(cellValues);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    List.of("Error processing file: " + e.getMessage()));
        }
    }*/
}
