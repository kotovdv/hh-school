package com.kotovdv.hhschool.tropical.island.logic;

import com.kotovdv.hhschool.tropical.island.exception.MissingCellException;
import com.kotovdv.hhschool.tropical.island.model.Island;
import com.kotovdv.hhschool.tropical.island.model.IslandCell;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author Dmitriy Kotov
 */
public class IslandNavigator {

    private final static List<Function<IslandCell, IslandCell>> sideFunctions = new ArrayList<>();

    static {
        sideFunctions.add(new AboveCellFunction());
        sideFunctions.add(new RightCellFunction());
        sideFunctions.add(new BelowCellFunction());
        sideFunctions.add(new LeftCellFunction());
    }


    public List<IslandCell> getSurroundingCells(Island island, IslandCell cell) {
        List<IslandCell> surroundingCells = new ArrayList<>();

        for (Function<IslandCell, IslandCell> sideFunction : sideFunctions) {
            IslandCell sideCell = sideFunction.apply(cell);
            if (island.isCellPresent(sideCell)) {
                surroundingCells.add(sideCell);
            }
        }

        return surroundingCells;
    }


    public boolean isLowland(Island island, IslandCell cell) {
        Optional<Integer> value = island.optionalValue(cell);
        if (!value.isPresent()) {
            throw new MissingCellException(cell);
        }
        Integer cellValue = value.get();

        List<IslandCell> surroundingCells = getSurroundingCells(island, cell);

        if (surroundingCells.size() != 4) {
            return false;
        }

        for (IslandCell surroundingCell : surroundingCells) {
            Optional<Integer> sideValue = island.optionalValue(surroundingCell);

            if (!sideValue.isPresent() || sideValue.get() < cellValue) {
                return false;
            }
        }

        return true;
    }


    private static class AboveCellFunction implements Function<IslandCell, IslandCell> {

        @Override
        public IslandCell apply(IslandCell cell) {
            return IslandCell.of(cell.getRowNumber() - 1, cell.getColumnNumber());
        }
    }

    private static class BelowCellFunction implements Function<IslandCell, IslandCell> {

        @Override
        public IslandCell apply(IslandCell cell) {
            return IslandCell.of(cell.getRowNumber() + 1, cell.getColumnNumber());
        }
    }

    private static class LeftCellFunction implements Function<IslandCell, IslandCell> {

        @Override
        public IslandCell apply(IslandCell cell) {
            return IslandCell.of(cell.getRowNumber(), cell.getColumnNumber() - 1);
        }
    }

    private static class RightCellFunction implements Function<IslandCell, IslandCell> {

        @Override
        public IslandCell apply(IslandCell cell) {
            return IslandCell.of(cell.getRowNumber(), cell.getColumnNumber() + 1);
        }
    }
}
