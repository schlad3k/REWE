package at.rewe;

import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ExcelReader {

  public TreeMap<String, String> readCellsFromUpload(MultipartFile file, TreeMap<String, CellReference> cellReferences) {
    TreeMap<String, String> cellValues = new TreeMap<>();

    try (InputStream excelStream = file.getInputStream();
         Workbook workbook = WorkbookFactory.create(excelStream)) {

      Sheet sheet = workbook.getSheetAt(0); // Read from the first sheet
      for (Map.Entry<String, CellReference> entry : cellReferences.entrySet()) {
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
