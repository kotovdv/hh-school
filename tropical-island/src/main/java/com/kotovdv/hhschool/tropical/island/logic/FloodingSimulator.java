package com.kotovdv.hhschool.tropical.island.logic;

import com.google.common.collect.HashBasedTable;
import com.kotovdv.hhschool.tropical.island.model.Island;
import com.kotovdv.hhschool.tropical.island.model.IslandCell;

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

        Island floodedIsland = createCopy(island);

        startFloodingProcess(floodedIsland, IslandCell.of(1, 1));

        return floodedIsland;
    }

    private void startFloodingProcess(Island island, IslandCell initialCell) {
        Deque<IslandCell> queue = new LinkedList<>();
        queue.add(initialCell);

        Set<IslandCell> visitedCells = new HashSet<>();
        while (!queue.isEmpty()) {
            IslandCell currentCell = queue.poll();

            if (islandNavigator.isLowland(island, currentCell)) {
                floodIslandCell(island, currentCell);
            }

            visitedCells.add(currentCell);

            //Process surrounding sides
            for (IslandCell nextCell : islandNavigator.getSurroundingCells(island, currentCell)) {
                if (hasBeenVisited(visitedCells, nextCell)) {
                    continue;
                }

                if (island.isBorder(nextCell)) {
                    continue;
                }

                queue.addFirst(currentCell);
                queue.addFirst(nextCell);
            }
        }
    }

    private boolean floodIslandCell(Island island, IslandCell islandCell) {
        Queue<IslandCell> queue = new LinkedList<>();
        queue.add(islandCell);

        Set<IslandCell> temporaryFloodedCells = new HashSet<>();
        int minValue = 1001;
        while (!queue.isEmpty()) {
            IslandCell currentCell = queue.poll();

            //It is impossible to flood area, that ends in a border
            if (island.isBorder(currentCell)) {
                return false;
            }

            //Skip processing if it was processed already
            if (hasBeenVisited(temporaryFloodedCells, currentCell)) {
                continue;
            }

            temporaryFloodedCells.add(currentCell);

            for (IslandCell nextCell : islandNavigator.getSurroundingCells(island, currentCell)) {
                int currentValue = island.value(currentCell);
                int nextValue = island.value(nextCell);

                int initialItemValue = island.value(currentCell);
                if (nextValue < minValue && nextValue != initialItemValue) {
                    minValue = nextValue;
                }

                //This path cant be flooded
                if (nextValue < currentValue) {
                    return false;
                } else if (nextValue == currentValue) {
                    queue.add(nextCell);
                }
            }

        }

        int floodingValue = minValue;
        temporaryFloodedCells.forEach(cell -> island.setValue(cell, floodingValue));

        return true;
    }

    /**
     * It is impossible to flood island that is less then 3x3
     */
    private boolean isNotFloodable(Island island) {
        return island.rowCount() < 3 || island.cellCount() < 3;
    }

    private Island createCopy(Island island) {
        return new Island(HashBasedTable.create(island.getTable()));
    }

    private boolean hasBeenVisited(Set<IslandCell> visitedCells, IslandCell nextCell) {
        return visitedCells.contains(nextCell);
    }

}

