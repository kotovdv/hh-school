package com.kotovdv.hhschool.tropical.island.logic;

import com.kotovdv.hhschool.tropical.island.model.Cell;

class FloodingQueueItem {

    private final Cell currentCell;
    private final Cell previousCell;


    FloodingQueueItem(Cell currentCell, Cell previousCell) {
        this.currentCell = currentCell;
        this.previousCell = previousCell;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public Cell getPreviousCell() {
        return previousCell;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FloodingQueueItem that = (FloodingQueueItem) o;

        if (currentCell != null ? !currentCell.equals(that.currentCell) : that.currentCell != null) return false;
        return previousCell != null ? previousCell.equals(that.previousCell) : that.previousCell == null;

    }

    @Override
    public int hashCode() {
        int result = currentCell != null ? currentCell.hashCode() : 0;
        result = 31 * result + (previousCell != null ? previousCell.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FloodingQueueItem{" +
                "currentCell=" + currentCell +
                ", previousCell=" + previousCell +
                '}';
    }
}