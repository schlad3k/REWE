package org.example;

public class CellReference {

    private int rowIndex;
    private int colIndex;

    public CellReference(String cellReference) {
        int[] indicies = convertToIndicies(cellReference);
        this.rowIndex = indicies[0];
        this.colIndex = indicies[1];
    }

    public int[] convertToIndicies(String cellReference) {
        String columnPart=cellReference.replaceAll("[^A-Za-z]", "");
        String rowPart = cellReference.replaceAll("[^0-9]","");

        rowIndex = Integer.parseInt(rowPart)-1;

        colIndex = 0;

        for(int i = 0; i<columnPart.length(); i++) {
            colIndex *= 26;
            colIndex += columnPart.charAt(i) - 'A' + 1;
        }
        colIndex--;

        System.out.println(cellReference+" : " + rowIndex +colIndex);

        return new int[]{rowIndex, colIndex};
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColIndex() {
        return colIndex;
    }

}
