package com.kotovdv.hhschool.tropical.island.model;

/**
 * @author Dmitriy Kotov
 */
public class Cell {

    private final int rowNumber;
    private final int columnNumber;

    public static Cell of(int rowNumber, int columnNumber) {
        return new Cell(rowNumber, columnNumber);
    }

    private Cell(int rowNumber, int columnNumber) {
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        if (rowNumber != cell.rowNumber) return false;
        return columnNumber == cell.columnNumber;

    }

    @Override
    public int hashCode() {
        int result = rowNumber;
        result = 31 * result + columnNumber;
        return result;
    }

    @Override
    public String toString() {
        return String.format("[%d,%d]", rowNumber, columnNumber);
    }
}
