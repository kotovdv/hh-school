package com.kotovdv.hhschool.tropical.island.logic;

import com.google.common.collect.HashBasedTable;
import com.kotovdv.hhschool.tropical.island.model.Island;
import com.kotovdv.hhschool.tropical.island.model.IslandCell;
import com.kotovdv.hhschool.tropical.island.model.IslandStep;

import java.util.*;

/**
 * @author Dmitriy Kotov
 */
public class FloodingSimulator {

    private final IslandNavigator islandNavigator = new IslandNavigator();

    public Island flood(Island island) {
        if (isNotFloodable(island)) {
            return island;
        }

        Island floodedIsland = new Island(HashBasedTable.create(island.getTable()));

        inDepthFlood(floodedIsland, new IslandStep(IslandCell.of(1, 1), IslandCell.of(1, 1)));

        return floodedIsland;
    }

    private boolean isNotFloodable(Island island) {
        return island.rowCount() < 3 || island.cellCount() < 3;
    }

    private void inDepthFlood(Island island, IslandStep initialItem) {
        Set<IslandCell> visitedCells = new HashSet<>();
        Map<IslandStep, Boolean> result = new HashMap<>();
        Deque<IslandStep> queue = new LinkedList<>();
        queue.add(initialItem);

        while (!queue.isEmpty()) {
            IslandStep currentItem = queue.poll();
            IslandCell currentCell = currentItem.getCurrentCell();

            visitedCells.add(currentCell);

            if (islandNavigator.isLowland(island, currentCell)) {
                result.put(currentItem, tryToFlood(island, currentItem));
            }

            boolean needFlooding = true;
            for (IslandCell nextCell : islandNavigator.getSurroundingCells(island, currentCell)) {
                if (visitedCells.contains(nextCell)) {
                    continue;
                }

                if (island.isBorder(nextCell) || nextCell.equals(currentItem.getPreviousCell())) {
                    continue;
                }

                IslandStep nextItem = new IslandStep(nextCell, currentCell);
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

    private boolean tryToFlood(Island island, IslandStep queueItem) {
        Queue<IslandStep> queue = new LinkedList<>();
        queue.add(queueItem);
        int initialItemValue = island.value(queueItem.getCurrentCell());

        Set<IslandCell> temporaryFloodedCells = new HashSet<>();
        int minValue = 1001;
        while (!queue.isEmpty()) {
            IslandStep currentItem = queue.poll();
            IslandCell currentCell = currentItem.getCurrentCell();

            //It is impossible to flood area, that ends in border
            if (island.isBorder(currentCell)) {
                return false;
            }

            //Skip processing if it was processed already
            if (temporaryFloodedCells.contains(currentCell)) {
                continue;
            }

            temporaryFloodedCells.add(currentCell);

            for (IslandCell nextCell : islandNavigator.getSurroundingCells(island, currentCell)) {
                int currentValue = island.value(currentCell);
                int nextValue = island.value(nextCell);

                if (nextValue < minValue && nextValue != initialItemValue) {
                    minValue = nextValue;
                }

                //This path cant be flooded
                if (nextValue < currentValue) {
                    return false;
                } else if (nextValue == currentValue) {
                    queue.add(new IslandStep(nextCell, currentCell));
                }
            }

        }

        int floodingValue = minValue;
        temporaryFloodedCells.forEach(cell -> island.setValue(cell, floodingValue));

        return true;
    }

}

