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
            if (!possibleOutputs.isEmpty()) {
                break;
            }

            for (int startIndex = 1 - groupSize, counter = 0; counter < groupSize && (groupSize + startIndex) <= sequence.length(); startIndex++, counter++) {
                int endIndex = groupSize + startIndex;

                String groupString = startIndex < 0
                        ? createForUncompleted(sequence, groupSize, startIndex, endIndex)
                        : sequence.substring(startIndex, endIndex);

                if (groupString.isEmpty() || groupString.startsWith("0")) {
                    continue;
                }

                int currentGroupValue = Integer.parseInt(groupString);

                if (startIndex >= groupSize && doesNotMatchPreviousGroup(sequence, startIndex, currentGroupValue)) {
                    continue;
                }

                if (isValidSequence(sequence, endIndex, currentGroupValue)) {
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

        if (Integer.toString(i + 1).length() > groupString.length()) {
            resultingGroupString = merge(String.valueOf(Integer.parseInt(nextGroupString) - 1), groupString, groupSize);
        } else {
            resultingGroupString = nextGroupString.substring(0, groupSize - groupString.length()) + groupString;
        }

        return resultingGroupString.substring(getSafeStartIndex(resultingGroupString.length() - groupSize), resultingGroupString.length());
    }

    private int getNumberOfValuesBetweenRanks(int j) {
        int upperBorder = getBorderValue(j);
        int lowerBorder = getBorderValue(j - 1);

        return (upperBorder - lowerBorder);
    }

    private int getBorderValue(int j) {
        return j > 0
                ? (int) Math.pow(10, j) - 1
                : 0;
    }

    private boolean isValidSequence(String sequence, int indexFrom, int previousNumber) {
        int previousGroupValue = previousNumber;

        for (int currentStartIndex = indexFrom; currentStartIndex < sequence.length(); ) {
            String actualNextValue = Integer.toString(previousGroupValue + 1);
            int safeEndIndex = getSafeEndIndex(sequence, currentStartIndex + actualNextValue.length());
            String existingNextValue = sequence.substring(currentStartIndex, safeEndIndex);
            if (actualNextValue.length() == existingNextValue.length()) {
                if (!actualNextValue.equals(existingNextValue)) {
                    return false;
                }
            } else {
                if (!actualNextValue.startsWith(existingNextValue)) {
                    return false;
                }
            }

            currentStartIndex = safeEndIndex;
            previousGroupValue = Integer.parseInt(actualNextValue);
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


    private String merge(String slaveSeq, String masterSeq, int expectedLength) {
        String modifiedSlaveSeq = appendEmptySpaces(slaveSeq, expectedLength);
        String modifiedMasterSeq = precedeWithEmptySpaces(masterSeq, expectedLength);

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = modifiedMasterSeq.length() - 1; i >= 0; i--) {
            char c = modifiedMasterSeq.charAt(i);
            if (c != ' ') {
                stringBuilder.insert(0, c);
            } else {
                stringBuilder.insert(0, modifiedSlaveSeq.charAt(i));
            }
        }

        return stringBuilder.toString().trim();
    }


    private String precedeWithEmptySpaces(String value, int expectedLength) {
        StringBuilder stringBuilder = new StringBuilder(value);
        while (stringBuilder.length() < expectedLength) {
            stringBuilder.insert(0, " ");
        }

        return stringBuilder.toString();
    }

    private String appendEmptySpaces(String value, int expectedLength) {
        StringBuilder stringBuilder = new StringBuilder(value);
        while (stringBuilder.length() < expectedLength) {
            stringBuilder.append(" ");
        }

        return stringBuilder.toString();
    }
}
