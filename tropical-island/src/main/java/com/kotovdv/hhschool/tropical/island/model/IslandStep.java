package com.kotovdv.hhschool.tropical.island.model;

public class IslandStep {

    private final IslandCell currentCell;
    private final IslandCell previousCell;

    public IslandStep(IslandCell currentCell, IslandCell previousCell) {
        this.currentCell = currentCell;
        this.previousCell = previousCell;
    }

    public IslandCell getCurrentCell() {
        return currentCell;
    }

    public IslandCell getPreviousCell() {
        return previousCell;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IslandStep that = (IslandStep) o;

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