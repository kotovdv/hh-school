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

        inDepthFlood(floodedIsland, new FloodingQueueItem(Cell.of(1, 1), Cell.of(1, 1)));

        return floodedIsland;
    }

    private boolean isNotFloodable(Island island) {
        return island.rowCount() < 3 || island.cellCount() < 3;
    }

    private void inDepthFlood(Island island, FloodingQueueItem initialItem) {
        Set<Cell> visitedCells = new HashSet<>();
        Map<FloodingQueueItem, Boolean> result = new HashMap<>();
        Deque<FloodingQueueItem> queue = new LinkedList<>();
        queue.add(initialItem);

        while (!queue.isEmpty()) {
            FloodingQueueItem currentItem = queue.poll();
            Cell currentCell = currentItem.getCurrentCell();

            visitedCells.add(currentCell);

            if (island.isLowland(currentCell)) {
                result.put(currentItem, tryToFlood(island, currentItem));
            }

            boolean needFlooding = true;
            for (Function<Cell, Cell> sideFunction : sideFunctions) {
                Cell nextCell = sideFunction.apply(currentCell);

                if (visitedCells.contains(nextCell)) {
                    continue;
                }

                if (island.isBorder(nextCell) || nextCell.equals(currentItem.getPreviousCell())) {
                    continue;
                }


                FloodingQueueItem nextItem = new FloodingQueueItem(nextCell, currentCell);
                Boolean flag = result.get(nextItem);
                if (flag == null) {
                    queue.addFirst(currentItem);
                    queue.addFirst(nextItem);
                    needFlooding = false;

                } else {
                    needFlooding = flag;
                }
            }

            if (needFlooding) {
                result.put(currentItem, tryToFlood(island, currentItem));
            }
        }
    }

    private boolean tryToFlood(Island island, FloodingQueueItem queueItem) {
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

