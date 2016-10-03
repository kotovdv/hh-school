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

    public BigInteger findIndexOf(String sequence) {
        List<BigInteger> possibleOutputs = new ArrayList<>();
        for (int groupSize = 1; groupSize <= sequence.length(); groupSize++) {

            for (int startIndex = 1 - groupSize, i = 0; i < groupSize && (groupSize + startIndex) <= sequence.length(); startIndex++, i++) {
                int endIndex = groupSize + startIndex;

                String currentGroup = createCurrentGroup(sequence, groupSize, startIndex, endIndex);

                if (currentGroup.isEmpty()) {
                    continue;
                }

                if (endlessSequence.contains(currentGroup, sequence.substring(endIndex, sequence.length()))) {
                    BigInteger indexOf = endlessSequence.indexOf(bigInteger(currentGroup));

                    possibleOutputs.add(indexOf.subtract(bigInteger(startIndex)));
                }
            }

            if (!possibleOutputs.isEmpty()) {
                break;
            }
        }

        return Collections.min(possibleOutputs);
    }


    private String createCurrentGroup(String sequence, int groupSize, int startIndex, int endIndex) {
        if (startIndex < 0) {
            return finishSequences(
                    sequence.substring(startIndex(startIndex), endIndex),
                    sequence.substring(endIndex, endIndex(sequence, endIndex + groupSize)),
                    groupSize);
        } else {
            return sequence.substring(startIndex, endIndex);
        }
    }

    private String finishSequences(String leftPart, String rightPart, int groupSize) {
        if (rightPart.startsWith("0")) {
            return "";
        }

        int leftPartLength = leftPart.length();

        String resultingString = plusOne(leftPart).length() > leftPartLength
                ? Sequences.merge(minusOne(rightPart), leftPart, groupSize)
                : rightPart.substring(0, groupSize - leftPartLength) + leftPart;

        int resultLength = resultingString.length();

        return resultingString.substring(startIndex(resultLength - groupSize), resultLength);
    }

}
