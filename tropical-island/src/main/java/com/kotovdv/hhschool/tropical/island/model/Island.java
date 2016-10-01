package com.kotovdv.hhschool.tropical.island.model;

import com.google.common.collect.Table;
import com.google.common.collect.Tables;

import java.util.Optional;

/**
 * @author Dmitriy Kotov
 */
public class Island {

    //TODO prb would be wise to switch to regular array after all
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

    public void setValue(IslandCell cell, int value) {
        setValue(cell.getRowNumber(), cell.getColumnNumber(), value);
    }

    public void setValue(int row, int column, int value) {
        table.put(row, column, value);
    }

    public int value(IslandCell cell) {
        return value(cell.getRowNumber(), cell.getColumnNumber());
    }

    public int value(int row, int cell) {
        return table.get(row, cell);
    }

    public Optional<Integer> optionalValue(IslandCell cell) {
        return optionalValue(cell.getRowNumber(), cell.getColumnNumber());
    }

    public Optional<Integer> optionalValue(int row, int cell) {
        return Optional.ofNullable(table.get(row, cell));
    }

    public boolean isBorder(IslandCell cell) {
        return isBorder(cell.getRowNumber(), cell.getColumnNumber());
    }


    public boolean isBorder(int row, int column) {
        if (row == 0 || row == (rowCount() - 1)) {
            return true;
        } else {
            return column == 0 || column == (cellCount() - 1);
        }
    }

    public boolean isCellPresent(IslandCell cell) {
        return isCellPresent(cell.getRowNumber(), cell.getColumnNumber());
    }

    public boolean isCellPresent(int row, int column) {
        return table.contains(row, column);
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
        StringBuilder table = new StringBuilder();
        for (int i = 0; i < rowCount(); i++) {
            for (int j = 0; j < cellCount(); j++) {
                table.append(value(i, j)).append(" ");
            }
            table.append(System.lineSeparator());
        }


        return table.toString();
    }

    public Table<Integer, Integer, Integer> getTable() {
        return Tables.unmodifiableTable(table);
    }
}
