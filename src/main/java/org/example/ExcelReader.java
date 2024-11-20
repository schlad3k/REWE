package org.example;

import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExcelReader {

    @Autowired
    private GitLabService gitLabService;

    public List<String> readCellsFromGitLab(String projectId, String filePath, String branch, List<String> cellReferences) {
        List<String> cellValues = new ArrayList<>();

        try (InputStream excelStream = gitLabService.fetchExcelFile(projectId, filePath, branch);
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
