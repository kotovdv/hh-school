package com.kotovdv.hhschool.endless.sequence.logic;

import com.kotovdv.hhschool.endless.sequence.util.Sequences;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.kotovdv.hhschool.endless.sequence.util.Sequences.endIndex;
import static com.kotovdv.hhschool.endless.sequence.util.Sequences.startIndex;
import static com.kotovdv.hhschool.endless.sequence.util.math.BigIntegerMath.bigInteger;
import static com.kotovdv.hhschool.endless.sequence.util.math.StringMath.minusOne;
import static com.kotovdv.hhschool.endless.sequence.util.math.StringMath.plusOne;

/**
 * @author Dmitriy Kotov
 */
public class SequenceLocationDetector {

    private final EndlessSequence endlessSequence = new EndlessSequence();

    /**
     * Searches for index of first appearance of given sequence in endless sequence
     */
    public BigInteger findIndex(String sequence) {
        List<BigInteger> possibleOutputs = new ArrayList<>();
        for (int numberLength = 1; numberLength <= sequence.length(); numberLength++) {

            for (int startIndex = 1 - numberLength; startIndex < 1; startIndex++) {
                int endIndex = startIndex + numberLength;

                String currentNumber = createNumber(sequence, numberLength, startIndex, endIndex);

                if (currentNumber.isEmpty()) {
                    continue;
                }

                if (endlessSequence.contains(currentNumber, sequence.substring(endIndex, sequence.length()))) {
                    possibleOutputs.add(getSequenceStartIndex(startIndex, currentNumber));
                }
            }

            if (!possibleOutputs.isEmpty()) {
                break;
            }
        }

        return Collections.min(possibleOutputs);
    }

    private BigInteger getSequenceStartIndex(int startIndex, String number) {
        BigInteger indexOfNumber = endlessSequence.indexOf(bigInteger(number));

        return indexOfNumber.subtract(bigInteger(startIndex));
    }


    private String createNumber(String sequence, int numberSize, int startIndex, int endIndex) {
        if (startIndex < 0) {
            return completeNumbers(
                    sequence.substring(startIndex(startIndex), endIndex),
                    sequence.substring(endIndex, endIndex(sequence, endIndex + numberSize)),
                    numberSize);
        } else {
            return sequence.substring(startIndex, endIndex);
        }
    }

    private String completeNumbers(String leftPart, String rightPart, int numberLength) {
        if (rightPart.startsWith("0")) {
            return "";
        }

        int leftPartLength = leftPart.length();

        String resultingString = plusOne(leftPart).length() > leftPartLength
                ? Sequences.merge(minusOne(rightPart), leftPart, numberLength)
                : rightPart.substring(0, numberLength - leftPartLength) + leftPart;

        int resultLength = resultingString.length();

        return resultingString.substring(startIndex(resultLength - numberLength), resultLength);
    }

}
