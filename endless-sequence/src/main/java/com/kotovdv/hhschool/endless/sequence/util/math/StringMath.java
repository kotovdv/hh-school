package com.kotovdv.hhschool.endless.sequence.util.math;

/**
 * @author Dmitriy Kotov
 */
public class StringMath {

    public static String plusOne(String value) {
        return plus(value, 1);
    }

    private static String plus(String value, int addition) {
        StringBuilder response = new StringBuilder(value);
        int remainder = addition;
        for (int i = response.length() - 1; i >= 0 && remainder > 0; i--) {
            int numericValue = Character.getNumericValue(response.charAt(i));
            numericValue++;
            if (numericValue == 10) {
                numericValue = 0;
                remainder++;
            }

            remainder--;
            response.setCharAt(i, Character.forDigit(numericValue, 10));
        }

        if (remainder == 1) {
            response.insert(0, '1');
        }

        return response.toString();
    }

    public static String minusOne(String value) {
        return minus(value, 1);
    }

    private static String minus(String value, int subtract) {
        StringBuilder response = new StringBuilder(value);
        int remainder = subtract;
        for (int i = response.length() - 1; i >= 0 && remainder > 0; i--) {
            int numericValue = Character.getNumericValue(response.charAt(i));
            if (numericValue == 0) {
                numericValue = 9;
                remainder++;
            } else {
                numericValue--;
            }

            remainder--;

            response.setCharAt(i, Character.forDigit(numericValue, 10));
        }

        if (response.length() > 1 && response.charAt(0) == '0') {
            response.deleteCharAt(0);
        }

        return response.toString();
    }
}
