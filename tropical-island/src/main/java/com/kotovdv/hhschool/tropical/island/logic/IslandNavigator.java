package com.kotovdv.hhschool.tropical.island.logic;

import com.kotovdv.hhschool.tropical.island.exception.IslandIsNotApplicableException;
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

    /**
     * @return All inner non seashore/non border cells
     */
    public List<IslandCell> getInnerCells(Island island) {
        if (!hasApplicableSize(island)) {
            throw new IslandIsNotApplicableException(island);
        }

        List<IslandCell> cells = new ArrayList<>();
        for (int i = 2; i < island.getRowsCount(); i++) {
            for (int j = 2; j < island.getColumnCount(i); j++) {
                cells.add(IslandCell.of(i, j));
            }
        }

        return cells;
    }


    /**
     * It is impossible to flood island that is less then 3x3 (4x4 with seashore)
     */
    public boolean hasApplicableSize(Island island) {
        return island.rowCount() >= 4 && island.cellCount() >= 4;
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
        int cellValue = island.value(cell);

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
