
package at.rewe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/excel")
public class ExcelController {

    @Autowired
    private ExcelReader excelReader;

    @PostMapping("/uploadFile")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file){
        return ResponseEntity.ok().body("file received successfully");
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
