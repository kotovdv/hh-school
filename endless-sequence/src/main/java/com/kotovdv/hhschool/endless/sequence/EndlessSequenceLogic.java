package com.kotovdv.hhschool.endless.sequence;

/**
 * @author Dmitriy Kotov
 */
public class EndlessSequenceLogic {


    public int getIndexOf(int startingValue) {
        int length = Integer.toString(startingValue).length();

        int index = 0;
        index = getStartingIndex(startingValue, length, index);
        index = index - length + 1;


        return index;
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


    public int getFirstAppropriateValue(String sequence) {
        for (int groupSize = 1; groupSize <= sequence.length(); groupSize++) {
            for (int startIndex = 1 - groupSize; startIndex < groupSize && (groupSize + startIndex) <= sequence.length(); startIndex++) {
                int endIndex = groupSize + startIndex;

                String groupString = startIndex < 0
                        ? createForUncompleted(sequence, groupSize, startIndex, endIndex)
                        : sequence.substring(startIndex, endIndex);


                int currentGroupValue = Integer.parseInt(groupString);

                if (startIndex >= groupSize && doesNotMatchPreviousGroup(sequence, startIndex, currentGroupValue)) {
                    continue;
                }

                if (checkSequence(sequence, endIndex, groupSize, currentGroupValue)) {
                    return currentGroupValue;
                }
            }
        }

        throw new RuntimeException();
    }

    private String createForUncompleted(String sequence, int groupSize, int startIndex, int endIndex) {
        String resultingGroupString;
        String groupString = startIndex < 0
                ? sequence.substring(0, endIndex)
                : sequence.substring(startIndex, endIndex);

        int nextGroupEndIndex = (endIndex + groupSize) <= sequence.length()
                ? endIndex + groupString.length()
                : sequence.length();
        String nextGroupString = sequence.substring(endIndex, nextGroupEndIndex);

        int i = Integer.parseInt(groupString);
        if (Integer.toString(i + 1).length() > groupSize - 1) {
            resultingGroupString = String.valueOf(Integer.parseInt(nextGroupString) - 1) + groupString;
        } else {
            resultingGroupString = nextGroupString + groupString;
        }

        return resultingGroupString;
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
            int currentEndIndex = j + groupSize <= sequence.length()
                    ? j + groupSize
                    : sequence.length();

            String currentGroup = sequence.substring(j, currentEndIndex);
            if (currentGroup.length() != groupSize) {
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
}
