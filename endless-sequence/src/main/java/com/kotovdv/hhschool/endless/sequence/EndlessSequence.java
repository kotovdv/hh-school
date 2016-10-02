package com.kotovdv.hhschool.endless.sequence;

import com.kotovdv.hhschool.endless.sequence.service.SequenceFactory;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

/**
 * @author Dmitriy Kotov
 */
public class EndlessSequence {

    public static void main(String[] args) {
        EndlessSequence endlessSequence = new EndlessSequence();
        endlessSequence.solve(System.in, System.out);
    }


    public void solve(final InputStream inputStream, final PrintStream outputStream) {
        List<String> sequence = SequenceFactory.create(inputStream);
        EndlessSequenceLogic endlessSequenceLogic = new EndlessSequenceLogic();

        sequence.forEach(currentSequence -> {
            int startingIndex = endlessSequenceLogic.getStartingIndexOf(currentSequence);
            outputStream.println(startingIndex);
        });

    }
}
