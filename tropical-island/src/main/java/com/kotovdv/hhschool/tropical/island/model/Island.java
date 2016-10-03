package com.kotovdv.hhschool.tropical.island.model;

import com.google.common.collect.Table;
import com.google.common.collect.Tables;

import java.util.Optional;

/**
 * @author Dmitriy Kotov
 */
public class Island {

    private final Table<Integer, Integer, Integer> islandMap;

    public Island(Table<Integer, Integer, Integer> islandMap) {
        this.islandMap = islandMap;
    }

    public int getRowsCount() {
        return islandMap.rowKeySet().size();
    }

    public int getColumnCount(int rowNum) {
        return islandMap.row(rowNum).size();
    }

    public void setValue(IslandCell cell, int value) {
        setValue(cell.getRowNumber(), cell.getColumnNumber(), value);
    }

    public void setValue(int row, int column, int value) {
        islandMap.put(row, column, value);
    }

    public int value(IslandCell cell) {
        return value(cell.getRowNumber(), cell.getColumnNumber());
    }

    public int value(int row, int cell) {
        return islandMap.get(row, cell);
    }

    public Optional<Integer> optionalValue(IslandCell cell) {
        return optionalValue(cell.getRowNumber(), cell.getColumnNumber());
    }

    public Optional<Integer> optionalValue(int row, int cell) {
        return Optional.ofNullable(islandMap.get(row, cell));
    }

    public boolean isCellPresent(IslandCell cell) {
        return isCellPresent(cell.getRowNumber(), cell.getColumnNumber());
    }

    public boolean isCellPresent(int row, int column) {
        return islandMap.contains(row, column);
    }

    public int sum() {
        return islandMap.values().stream().mapToInt(Integer::intValue).sum();
    }

    public int cellCount() {
        return islandMap.columnKeySet().size();
    }

    public int rowCount() {
        return islandMap.rowKeySet().size();
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

    public Table<Integer, Integer, Integer> getIslandMap() {
        return Tables.unmodifiableTable(islandMap);
    }
}
