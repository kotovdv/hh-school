package com.kotovdv.hhschool.endless.sequence.logic;

import java.math.BigInteger;

import static com.kotovdv.hhschool.endless.sequence.util.Sequences.endIndex;
import static com.kotovdv.hhschool.endless.sequence.util.math.BigIntegerMath.bigInteger;
import static com.kotovdv.hhschool.endless.sequence.util.math.BigIntegerMath.multiply;
import static com.kotovdv.hhschool.endless.sequence.util.math.StringMath.plusOne;
import static java.math.BigInteger.TEN;

/**
 * @author Dmitriy Kotov
 */
public class EndlessSequence {

    /**
     * Returns starting index for given number
     */
    public BigInteger indexOf(BigInteger number) {
        String stringRepresentation = number.toString();
        int length = stringRepresentation.length();

        return getStartingIndex(number, length)
                .subtract(bigInteger(length))
                .add(BigInteger.ONE);
    }


    /**
     * Checks if endless sequence contains given sequence
     *
     * @param sequence                 part of sequence we are looking for
     * @param initialPrecedingSequence part of sequence that should precede given sequence
     * @return true if endless sequence contains given part, false otherwise
     */
    public boolean contains(String initialPrecedingSequence, String sequence) {
        String precedingSequence = initialPrecedingSequence;

        for (int startIndex = 0; startIndex < sequence.length(); ) {

            String expectedNextValue = plusOne(precedingSequence);
            int endIndex = endIndex(sequence, startIndex + expectedNextValue.length());
            String actualNextValue = sequence.substring(startIndex, endIndex);

            if (areDifferent(expectedNextValue, actualNextValue)) {
                return false;
            }

            startIndex = endIndex;
            precedingSequence = expectedNextValue;
        }


        return true;
    }

    private boolean areDifferent(String actualNextValue, String existingNextValue) {
        if (actualNextValue.length() == existingNextValue.length()) {
            if (!actualNextValue.equals(existingNextValue)) {
                return true;
            }
        } else {
            if (!actualNextValue.startsWith(existingNextValue)) {
                return true;
            }
        }

        return false;
    }


    private BigInteger getStartingIndex(BigInteger number, int length) {
        BigInteger index = BigInteger.ZERO;

        for (int rank = 1; rank < length + 1; rank++) {
            BigInteger biRank = bigInteger(rank);

            if (rank == length) {
                BigInteger borderValue = getBorderValue(rank - 1);
                index = index.add(multiply(biRank, number.subtract(borderValue)));
            } else {
                index = index.add(multiply(biRank, valuesBetweenPreviousAndGivenRank(rank)));
            }
        }

        return index;
    }

    private BigInteger valuesBetweenPreviousAndGivenRank(int nextRank) {
        BigInteger upperBorder = getBorderValue(nextRank);
        BigInteger lowerBorder = getBorderValue(nextRank - 1);

        return upperBorder.subtract(lowerBorder);
    }

    private BigInteger getBorderValue(int power) {
        return power <= 0
                ? BigInteger.ZERO
                : TEN.pow(power).subtract(BigInteger.ONE);
    }

}
