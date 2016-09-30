package com.kotovdv.hhschool.tropical.island.model.tree;

/**
 * @author Dmitriy Kotov
 */
public class IslandTreeItem {

    private final int rowNum;
    private final int cellNum;
    private final int value;


    public IslandTreeItem(int rowNum, int cellNum, int value) {
        this.rowNum = rowNum;
        this.cellNum = cellNum;
        this.value = value;
    }
}
