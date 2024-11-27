package at.rewe;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ExcelReader {

    public Map<String,String> readCellsFromUpload(MultipartFile file, Map<String,CellReference> cellReferences) {
        Map<String,String> cellValues = new HashMap<String,String>();

        try (InputStream excelStream = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(excelStream)) {

            Sheet sheet = workbook.getSheetAt(0); // Read from the first sheet
            for (Map.Entry<String,CellReference> entry : cellReferences.entrySet()) {
                String cellValue = getCellValue(entry.getValue().getRowIndex(), entry.getValue().getColIndex(), sheet);
                cellValues.put(entry.getKey(), cellValue);
            }
        } catch (Exception e) {
            System.out.println("Error reading Excel file: " + e.getMessage());
            e.printStackTrace();
        }
        return cellValues;
    }

    private String getCellValue(int rowIndex, int colIndex, Sheet sheet) {
        Row row = sheet.getRow(rowIndex);
        if (row != null) {
            Cell cell = row.getCell(colIndex);
            if (cell != null) {
                return cell.toString();
            } else {
                return "Cell (" + rowIndex + ", " + colIndex + ") is empty.";
            }
        } else {
            return "Row " + rowIndex + " does not exist.";
        }
    }
}