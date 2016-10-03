package com.kotovdv.hhschool.tropical.island.util;

import com.kotovdv.hhschool.tropical.island.model.FloodingResult;
import com.kotovdv.hhschool.tropical.island.model.IslandCell;

import java.util.Set;

/**
 * @author Dmitriy Kotov
 */
public class FloodingResultFactory {

    public static FloodingResult successfulFlooding(Set<IslandCell> visitedCells) {
        return new FloodingResult(true, visitedCells);
    }

    public static FloodingResult failedFlooding(Set<IslandCell> visitedCells) {
        return new FloodingResult(false, visitedCells);
    }
}