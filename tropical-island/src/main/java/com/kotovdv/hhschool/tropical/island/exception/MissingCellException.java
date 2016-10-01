package com.kotovdv.hhschool.tropical.island.exception;

import com.kotovdv.hhschool.tropical.island.model.IslandCell;

/**
 * @author Dmitriy Kotov
 */
public class MissingCellException extends RuntimeException {

    public MissingCellException(IslandCell cell) {
        super(String.format("Missing cell [%s]", cell));
    }
}
