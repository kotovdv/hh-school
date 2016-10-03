package com.kotovdv.hhschool.endless.sequence;

import com.kotovdv.hhschool.endless.sequence.logic.SequenceLocationDetector;
import com.kotovdv.hhschool.endless.sequence.util.SequenceFactory;

import java.io.InputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import java.util.List;

/**
 * @author Dmitriy Kotov
 */
public class EndlessSequence {

    public static void main(String[] args) {
        EndlessSequence endlessSequence = new EndlessSequence();
        endlessSequence.solve(System.in, System.out);
    }

    private void solve(final InputStream inputStream, final PrintStream outputStream) {
        List<String> sequence = SequenceFactory.create(inputStream);
        SequenceLocationDetector sequenceLocationDetector = new SequenceLocationDetector();

        sequence.forEach(currentSequence -> {
            BigInteger index = sequenceLocationDetector.findIndex(currentSequence);
            outputStream.println(index);
        });

    }
}
