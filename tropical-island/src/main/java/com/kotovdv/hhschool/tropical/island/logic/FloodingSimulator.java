package com.kotovdv.hhschool.tropical.island.logic;

import com.google.common.collect.HashBasedTable;
import com.kotovdv.hhschool.tropical.island.model.FloodingResult;
import com.kotovdv.hhschool.tropical.island.model.Island;
import com.kotovdv.hhschool.tropical.island.model.IslandCell;

import java.util.*;

import static com.kotovdv.hhschool.tropical.island.util.FloodingResultFactory.failedFlooding;
import static com.kotovdv.hhschool.tropical.island.util.FloodingResultFactory.successfulFlooding;

/**
 * @author Dmitriy Kotov
 */
public class FloodingSimulator {

    private final IslandNavigator islandNavigator = new IslandNavigator();

    public Island flood(Island island) {
        if (!islandNavigator.hasApplicableSize(island)) {
            return island;
        }

        Island floodedIsland = createCopy(island);

        Queue<IslandCell> queue = new LinkedList<>();
        queue.addAll(islandNavigator.getInnerCells(floodedIsland));

        while (!queue.isEmpty()) {
            IslandCell currentCell = queue.poll();

            if (islandNavigator.isLowland(floodedIsland, currentCell)) {
                FloodingResult floodingResult;
                do {
                    floodingResult = attemptToFloodCell(floodedIsland, currentCell);
                    if (!floodingResult.wasSuccessful()) {
                        //Do not attempt to flood cells, that failed to be flooded before
                        queue.removeAll(floodingResult.getCells());
                    }
                } while (floodingResult.wasSuccessful());
            }
        }

        return floodedIsland;
    }

    private FloodingResult attemptToFloodCell(Island island, IslandCell islandCell) {
        Queue<IslandCell> queue = new LinkedList<>();
        queue.add(islandCell);

        Set<IslandCell> visitedCells = new HashSet<>();
        int minValue = 1001;
        while (!queue.isEmpty()) {
            IslandCell currentCell = queue.poll();
            //Skip processing if it was processed already
            if (hasBeenVisited(visitedCells, currentCell)) {
                continue;
            }

            for (IslandCell nextCell : islandNavigator.getSurroundingCells(island, currentCell)) {
                int currentValue = island.value(currentCell);
                int nextValue = island.value(nextCell);

                if (nextValue < currentValue) {
                    //This path cant be flooded
                    return failedFlooding(visitedCells);
                }

                if (nextValue < minValue && nextValue != currentValue) {
                    minValue = nextValue;
                }

                if (nextValue == currentValue) {
                    queue.add(nextCell);
                }
            }

            visitedCells.add(currentCell);
        }

        floodCells(island, visitedCells, minValue);

        return successfulFlooding(visitedCells);
    }

    private void floodCells(Island island, Set<IslandCell> visitedCells, int level) {
        visitedCells.forEach(cell -> island.setValue(cell, level));
    }

    private boolean hasBeenVisited(Set<IslandCell> visitedCells, IslandCell nextCell) {
        return visitedCells.contains(nextCell);
    }

    private Island createCopy(Island island) {
        return new Island(HashBasedTable.create(island.getIslandMap()));
    }
}


