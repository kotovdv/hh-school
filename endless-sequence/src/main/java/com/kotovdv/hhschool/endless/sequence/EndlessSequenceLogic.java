package com.kotovdv.hhschool.endless.sequence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Dmitriy Kotov
 */
public class EndlessSequenceLogic {


    public int getStartingIndexOf(String sequence) {
        List<Integer> possibleOutputs = new ArrayList<>();
        for (int groupSize = 1; groupSize <= sequence.length(); groupSize++) {
            for (int startIndex = 1 - groupSize, counter = 0; counter < groupSize && (groupSize + startIndex) <= sequence.length(); startIndex++, counter++) {
                int endIndex = groupSize + startIndex;

                String groupString = startIndex < 0
                        ? createForUncompleted(sequence, groupSize, startIndex, endIndex)
                        : sequence.substring(startIndex, endIndex);

                if (groupString.isEmpty()) {
                    continue;
                }

                int currentGroupValue = Integer.parseInt(groupString);

                if (startIndex >= groupSize && doesNotMatchPreviousGroup(sequence, startIndex, currentGroupValue)) {
                    continue;
                }

                if (checkSequence(sequence, endIndex, groupSize, currentGroupValue)) {
                    int indexOf = getIndexOf(currentGroupValue);

                    possibleOutputs.add(indexOf - startIndex);
                }
            }
        }

        return Collections.min(possibleOutputs);
    }

    private int getStartingIndex(int startingValue, int length, int index) {
        for (int rank = 1; rank < length + 1; rank++) {
            if (rank == length) {
                int borderValue = getBorderValue(rank - 1);
                index += rank * (startingValue - borderValue);
            } else {
                index += rank * getNumberOfValuesBetweenRanks(rank);
            }
        }
        return index;
    }


    private int getIndexOf(int startingValue) {
        int length = Integer.toString(startingValue).length();

        int index = 0;
        index = getStartingIndex(startingValue, length, index);
        index = index - length + 1;


        return index;
    }

    private String createForUncompleted(String sequence, int groupSize, int startIndex, int endIndex) {
        String resultingGroupString;

        String groupString = sequence.substring(getSafeStartIndex(startIndex), endIndex);

        int nextGroupEndIndex = getSafeEndIndex(sequence, endIndex + groupSize);

        String nextGroupString = sequence.substring(endIndex, nextGroupEndIndex);

        int i = Integer.parseInt(groupString);

        if (nextGroupString.startsWith("0")) {
            return "";
        }

        if (Integer.toString(i + 1).length() > groupSize - 1) {
            resultingGroupString = String.valueOf(Integer.parseInt(nextGroupString) - 1) + groupString;
        } else {
            resultingGroupString = nextGroupString.substring(0, groupSize - groupString.length()) + groupString;
        }

        return resultingGroupString.substring(0, groupSize);
    }

    private int getNumberOfValuesBetweenRanks(int j) {
        int upperBorder = getBorderValue(j);
        int lowerBorder = getBorderValue(j - 1);

        return (upperBorder - lowerBorder);
    }

    private int getBorderValue(int j) {
        return j <= 0
                ? 0
                : (int) Math.pow(10, j) - 1;
    }


    private boolean checkSequence(String sequence, int indexFrom, int groupSize, int previousNumber) {
        int previousGroupValue = previousNumber;

        for (int j = indexFrom; j < sequence.length(); ) {
            int currentEndIndex = getSafeEndIndex(sequence, j + groupSize);

            String currentGroup = sequence.substring(j, currentEndIndex);
            if (currentGroup.length() != groupSize || Integer.toString(previousGroupValue + 1).length() > groupSize) {
                String expectedNextValue = Integer.toString(previousGroupValue + 1);

                return expectedNextValue.startsWith(currentGroup);
            }


            int currentGroupValue = Integer.parseInt(currentGroup);
            if (currentGroupValue != (previousGroupValue + 1)) {
                return false;
            }

            previousGroupValue = currentGroupValue;
            j = currentEndIndex;
        }


        return true;
    }

    private boolean doesNotMatchPreviousGroup(String sequence, int i, int currentGroupValue) {
        int previousGroupValue = currentGroupValue - 1;
        String previousGroup = Integer.toString(previousGroupValue);

        return !previousGroup.endsWith(sequence.substring(0, i));
    }

    private int getSafeStartIndex(int unsafeIndex) {
        return unsafeIndex >= 0 ? unsafeIndex : 0;
    }

    private int getSafeEndIndex(String sequence, int unsafeIndex) {
        return unsafeIndex <= sequence.length()
                ? unsafeIndex
                : sequence.length();
    }

}
