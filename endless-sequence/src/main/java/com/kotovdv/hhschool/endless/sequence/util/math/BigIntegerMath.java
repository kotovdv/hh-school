package com.kotovdv.hhschool.endless.sequence.util.math;

import java.math.BigInteger;

/**
 * @author Dmitriy Kotov
 */
public class BigIntegerMath {

    public static BigInteger bigInteger(String value) {
        return new BigInteger(value);
    }

    public static BigInteger bigInteger(int value) {
        return BigInteger.valueOf(value);
    }

    public static BigInteger multiply(BigInteger first, BigInteger second) {
        return first.multiply(second);
    }

}
