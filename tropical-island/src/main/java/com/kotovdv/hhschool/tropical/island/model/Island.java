package com.kotovdv.hhschool.tropical.island.model;

import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import com.kotovdv.hhschool.tropical.island.exception.MissingCellException;

import java.util.Optional;

/**
 * @author Dmitriy Kotov
 */
public class Island {

    private final Table<Integer, Integer, Integer> table;

    public Island(Table<Integer, Integer, Integer> table) {
        this.table = table;
    }

    public int getRowsCount() {
        return table.rowKeySet().size();
    }

    public int getColumnCount(int rowNum) {
        return table.row(rowNum).size();
    }

    public void setValue(Cell cell, int value) {
        setValue(cell.getRowNumber(), cell.getColumnNumber(), value);
    }

    public void setValue(int row, int column, int value) {
        table.put(row, column, value);
    }

    public int value(Cell cell) {
        return value(cell.getRowNumber(), cell.getColumnNumber());
    }

    public int value(int row, int cell) {
        return table.get(row, cell);
    }

    public Optional<Integer> optionalValue(Cell cell) {
        return optionalValue(cell.getRowNumber(), cell.getColumnNumber());
    }

    public Optional<Integer> optionalValue(int row, int cell) {
        return Optional.ofNullable(table.get(row, cell));
    }

    public Optional<Integer> valueAbove(int row, int column) {
        return getFromOptional(optionalValue(row - 1, column));
    }

    public Optional<Integer> valueBelow(int row, int column) {
        return getFromOptional(optionalValue(row + 1, column));
    }

    public Optional<Integer> leftValue(int row, int column) {
        return getFromOptional(optionalValue(row, column - 1));
    }

    public Optional<Integer> rightValue(int row, int column) {
        return getFromOptional(optionalValue(row, column + 1));
    }

    public boolean isBorder(Cell cell) {
        return isBorder(cell.getRowNumber(), cell.getColumnNumber());
    }


    public boolean isBorder(int row, int column) {
        if (row == 0 || row == (cellCount() - 1)) {
            return true;
        } else {
            return column == 0 || column == (rowCount() - 1);
        }
    }

    public boolean isCellPresent(Cell cell) {
        return isCellPresent(cell.getRowNumber(), cell.getColumnNumber());
    }

    public boolean isCellPresent(int row, int column) {
        return table.contains(row, column);
    }

    public boolean isLowland(int row, int column) {
        Optional<Integer> value = optionalValue(row, column);
        if (!value.isPresent()) {
            throw new MissingCellException(row, column);
        }
        Integer cellValue = value.get();

        Optional<Integer> valueAbove = valueAbove(row, column);
        Optional<Integer> valueBelow = valueBelow(row, column);
        Optional<Integer> leftValue = leftValue(row, column);
        Optional<Integer> rightValue = rightValue(row, column);


        return isLessOrEqualTo(cellValue, valueAbove) &&
                isLessOrEqualTo(cellValue, valueBelow) &&
                isLessOrEqualTo(cellValue, leftValue) &&
                isLessOrEqualTo(cellValue, rightValue);
    }


    //TODO DO SMTH ABOUT IT
    private boolean isLessOrEqualTo(Integer cellValue, Optional<Integer> valueAbove) {
        return valueAbove.isPresent() && valueAbove.get() >= cellValue;
    }


    public int sum() {
        return table.values().stream().mapToInt(Integer::intValue).sum();
    }

    public int cellCount() {
        return table.columnKeySet().size();
    }

    public int rowCount() {
        return table.rowKeySet().size();
    }

    @Override
    public String toString() {
        return "Island{" +
                "table=" + table +
                '}';
    }

    //TODO THINK
    private Optional<Integer> getFromOptional(Optional<Integer> value) {
        return value.isPresent()
                ? Optional.ofNullable(value.get())
                : Optional.empty();
    }


    public Table<Integer, Integer, Integer> getTable() {
        return Tables.unmodifiableTable(table);
    }
}
