package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class ExcelReader {
    private String filePath;

    public ExcelReader(String filePath) {
        this.filePath = filePath;
    }

    public List<String> readCells(List<String> cellReferences) {
        List<String> cellValues = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);  // Read from the first sheet
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