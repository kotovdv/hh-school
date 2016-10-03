package com.kotovdv.hhschool.tropical.island.model;

import java.util.Collections;
import java.util.Set;

public class FloodingResult {

    private final boolean result;
    private final Set<IslandCell> cells;

    public FloodingResult(boolean result, Set<IslandCell> cells) {
        this.result = result;
        this.cells = cells;
    }

    public boolean wasSuccessful() {
        return result;
    }

    public Set<IslandCell> getCells() {
        return Collections.unmodifiableSet(cells);
    }
}