package com.kotovdv.hhschool.tropical.island.logic;

import com.google.common.collect.HashBasedTable;
import com.kotovdv.hhschool.tropical.island.model.Cell;
import com.kotovdv.hhschool.tropical.island.model.Island;

import java.util.*;
import java.util.function.Function;

/**
 * @author Dmitriy Kotov
 */
public class FloodingSimulator {

    private final static List<Function<Cell, Cell>> sideFunctions = new ArrayList<>();

    static {
        sideFunctions.add(new AboveCellFunction());
        sideFunctions.add(new RightCellFunction());
        sideFunctions.add(new BelowCellFunction());
        sideFunctions.add(new LeftCellFunction());
    }


    public Island flood(Island island) {
        if (isNotFloodable(island)) {
            return island;
        }

        Island floodedIsland = new Island(HashBasedTable.create(island.getTable()));

        for (int i = 1; i < floodedIsland.rowCount(); i++) {
            for (int j = 1; j < floodedIsland.cellCount(); j++) {
                if (floodedIsland.isLowland(i, j)) {
                    Cell currentCell = Cell.of(i, j);

                    FloodingQueueItem queueItem = new FloodingQueueItem(currentCell, currentCell);

                    if (naiveFlood(floodedIsland, queueItem)) {
                        i = 1;
                        j = 1;
                    }
                }

            }
        }

        return floodedIsland;
    }

    private boolean isNotFloodable(Island island) {
        return island.rowCount() < 3 || island.cellCount() < 3;
    }


    private boolean naiveFlood(Island island, FloodingQueueItem queueItem) {
        Queue<FloodingQueueItem> queue = new LinkedList<>();
        queue.add(queueItem);
        int initialItemValue = island.value(queueItem.getCurrentCell());

        Set<Cell> temporaryFloodedCells = new HashSet<>();
        int minValue = 1001;
        while (!queue.isEmpty()) {
            FloodingQueueItem currentItem = queue.poll();
            Cell currentCell = currentItem.getCurrentCell();

            //It is impossible to flood area, that ends in border
            if (island.isBorder(currentCell)) {
                return false;
            }

            //Skip processing if it was processed already
            if (temporaryFloodedCells.contains(currentCell)) {
                continue;
            }

            temporaryFloodedCells.add(currentCell);

            for (Function<Cell, Cell> sideFunction : sideFunctions) {
                Cell nextCell = sideFunction.apply(currentCell);

                //No need to process it, lets go to the next one
                if (isTheCellWeCameFrom(nextCell, currentItem.getPreviousCell())) {
                    continue;
                }

                int currentValue = island.value(currentCell);
                int nextValue = island.value(nextCell);

                if (nextValue < minValue && nextValue != initialItemValue) {
                    minValue = nextValue;
                }

                //This path cant be flooded
                if (nextValue < currentValue) {
                    return false;
                } else if (nextValue == currentValue) {
                    queue.add(new FloodingQueueItem(nextCell, currentCell));
                }
            }

        }

        int floodingValue = minValue;
        temporaryFloodedCells.forEach(cell -> island.setValue(cell, floodingValue));

        return true;
    }

    private boolean isTheCellWeCameFrom(Cell previousCell, Cell nextCell) {
        return nextCell.equals(previousCell);
    }

    private static class AboveCellFunction implements Function<Cell, Cell> {

        @Override
        public Cell apply(Cell cell) {
            return Cell.of(cell.getRowNumber() - 1, cell.getColumnNumber());
        }
    }

    private static class BelowCellFunction implements Function<Cell, Cell> {

        @Override
        public Cell apply(Cell cell) {
            return Cell.of(cell.getRowNumber() + 1, cell.getColumnNumber());
        }
    }

    private static class LeftCellFunction implements Function<Cell, Cell> {

        @Override
        public Cell apply(Cell cell) {
            return Cell.of(cell.getRowNumber(), cell.getColumnNumber() - 1);
        }
    }

    private static class RightCellFunction implements Function<Cell, Cell> {

        @Override
        public Cell apply(Cell cell) {
            return Cell.of(cell.getRowNumber(), cell.getColumnNumber() + 1);
        }
    }

}

