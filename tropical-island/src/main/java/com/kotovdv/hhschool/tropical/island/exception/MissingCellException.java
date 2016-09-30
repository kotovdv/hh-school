package com.kotovdv.hhschool.tropical.island.exception;

/**
 * @author Dmitriy Kotov
 */
public class MissingCellException extends RuntimeException {

    public MissingCellException(int row, int column) {
        super(String.format("Missing of in [%d,%d]", row, column));
    }
}
