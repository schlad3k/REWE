package at.rewe;

public class CellReference {

  private int rowIndex;
  private int colIndex;
  private String cellReference;


  public CellReference(String cr) {
    this.cellReference = cr;
    convertToIndicies();
  }

  public int[] convertToIndicies() {
    String columnPart=cellReference.replaceAll("[^A-Za-z]", "");
    String rowPart = cellReference.replaceAll("[^0-9]","");

    rowIndex = Integer.parseInt(rowPart)-1;

    colIndex = 0;

    for(int i = 0; i<columnPart.length(); i++) {
      colIndex *= 26;
      colIndex += columnPart.charAt(i) - 'A' + 1;
    }
    colIndex--;


    return new int[]{rowIndex, colIndex};
  }

  public int getRowIndex() {
    return rowIndex;
  }

  public int getColIndex() {
    return colIndex;
  }

}