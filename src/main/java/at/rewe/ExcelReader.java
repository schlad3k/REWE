package at.rewe;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExcelReader {



    public List<String> readCellsFromUpload(MultipartFile file, List<String> cellReferences) {
        List<String> cellValues = new ArrayList<>();

        try (InputStream excelStream = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(excelStream)) {

            Sheet sheet = workbook.getSheetAt(0); // Read from the first sheet
            for (String cellReference : cellReferences) {
                CellReference ref = new CellReference(cellReference);
                String cellValue = getCellValue(ref.getRowIndex(), ref.getColIndex(), sheet);
                cellValues.add("Value at " + cellReference + ": " + cellValue);
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