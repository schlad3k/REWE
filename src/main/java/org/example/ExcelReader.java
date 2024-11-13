package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
    public static void main(String[] args) {
        String excelFilePath = "C:\\Users\\simon\\OneDrive - tgm - Die Schule der Technik\\Dokumente\\4. Klasse\\ITP\\ARTIKEL-STAMMBLATT.xlsx";
        List<int[]> cellCoordinates = new ArrayList<>();
        cellCoordinates.add(convertToCellIndices("Q13"));
        cellCoordinates.add(convertToCellIndices("I15"));
        cellCoordinates.add(convertToCellIndices("B9"));
        try (FileInputStream fis = new FileInputStream(new File(excelFilePath));
             Workbook workbook = new XSSFWorkbook(fis)) {

            // Das erste Blatt auswählen
            Sheet sheet = workbook.getSheetAt(0);
            for (int[] cellReference : cellCoordinates) {
                int rowIndex = cellReference[0];
                int colIndex = cellReference[1];

                // Get the row and cell based on the coordinates
                Row row = sheet.getRow(rowIndex);
                if (row != null) {
                    Cell cell = row.getCell(colIndex);
                    if (cell != null) {
                        // Print the cell's value
                        System.out.println("Value at (" + rowIndex + ", " + colIndex + "): " + cell.toString());
                    } else {
                        System.out.println("Cell at (" + rowIndex + ", " + colIndex + ") is empty.");
                    }
                } else {
                    System.out.println("Row " + rowIndex + " does not exist in the sheet.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static String getCellValue(int rowIndex, int colIndex, Sheet sheet) {
        Row row = sheet.getRow(rowIndex);
        if (row != null) {
            Cell cell = row.getCell(colIndex);
            if (cell != null) {
                // Directly print the cell value as a string
                return cell.getStringCellValue();
            } else {
                System.out.println("Q13 cell is empty.");
            }
        } else {
            System.out.println("Row 13 does not exist in the sheet.");
        }
        return null;
    }
    public static int[] convertToCellIndices(String cellReference) {
        // Extract the column part (letters) and row part (numbers) from the reference
        String columnPart = cellReference.replaceAll("[^A-Za-z]", "");  // Extract letters (e.g., "I")
        String rowPart = cellReference.replaceAll("[^0-9]", "");  // Extract digits (e.g., "15")

        // Convert the row part to an integer (1-based index in Excel)
        int rowIndex = Integer.parseInt(rowPart) - 1;  // Adjust to 0-based index for Apache POI

        // Convert the column part to a column index (0-based)
        int colIndex = 0;
        for (int i = 0; i < columnPart.length(); i++) {
            colIndex *= 26;  // Shift the column index by a factor of 26
            colIndex += columnPart.charAt(i) - 'A';  // Convert letter to a number (A=1, B=2, ..., Z=26)
        }
        //colIndex -= 1;  // Adjust to 0-based index

        return new int[] {rowIndex, colIndex};
    }

}

