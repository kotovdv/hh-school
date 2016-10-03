package com.kotovdv.hhschool.endless.sequence.util;

/**
 * @author Dmitriy Kotov
 */
public class Sequences {

    /**
     * @return given index, if it is greater of equal to 0, 0 otherwise
     */
    public static int startIndex(int unsafeIndex) {
        return unsafeIndex >= 0 ? unsafeIndex : 0;
    }

    /**
     * @return given index, if it is less or equal to sequence.length() size, sequence.length otherwise
     */
    public static int endIndex(String sequence, int unsafeIndex) {
        return unsafeIndex <= sequence.length()
                ? unsafeIndex
                : sequence.length();
    }

    /**
     * Merges two given sequences into one sequence of expectedLength
     * Values from masterSeq take priority over values of childSeq
     */
    public static String merge(String childSeq, String masterSeq, int expectedLength) {
        String modifiedChildSeq = appendEmptySpaces(childSeq, expectedLength);
        String modifiedMasterSeq = precedeWithEmptySpaces(masterSeq, expectedLength);

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = modifiedMasterSeq.length() - 1; i >= 0; i--) {
            char c = modifiedMasterSeq.charAt(i);
            if (c != ' ') {
                stringBuilder.insert(0, c);
            } else {
                stringBuilder.insert(0, modifiedChildSeq.charAt(i));
            }
        }

        return stringBuilder.toString().trim();
    }


    private static String precedeWithEmptySpaces(String value, int expectedLength) {
        StringBuilder stringBuilder = new StringBuilder(value);
        while (stringBuilder.length() < expectedLength) {
            stringBuilder.insert(0, " ");
        }

        return stringBuilder.toString();
    }

    private static String appendEmptySpaces(String value, int expectedLength) {
        StringBuilder stringBuilder = new StringBuilder(value);
        while (stringBuilder.length() < expectedLength) {
            stringBuilder.append(" ");
        }

        return stringBuilder.toString();
    }
}
