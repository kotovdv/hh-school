package com.kotovdv.hhschool.tropical.island.exception;

import com.kotovdv.hhschool.tropical.island.model.Island;

/**
 * @author Dmitriy Kotov
 */
public class IslandIsNotApplicableException extends RuntimeException {

    public IslandIsNotApplicableException(Island island) {
        super("Island \n " + island + "\n is not applicable for extracting inner cells");
    }
}