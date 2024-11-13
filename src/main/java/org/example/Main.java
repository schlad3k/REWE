package org.example;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\simon\\OneDrive - tgm - Die Schule der Technik\\Dokumente\\4. Klasse\\ITP\\ARTIKEL-STAMMBLATT.xlsx";  // Replace with your actual path

        ExcelReader reader = new ExcelReader(filePath);

        // List of cell references to read from the Excel file
        List<String> cellReferences = Arrays.asList("Q13", "B9", "I15", "B19", "L19", "T19", "B27", "V27", "B31", "B33", "B35");

        // Read and print the values of the specified cells
        List<String> cellValues = reader.readCells(cellReferences);
        System.out.println("Reading specified cells from Excel file:");
        for (String value : cellValues) {
            System.out.println(value);
        }
    }
}