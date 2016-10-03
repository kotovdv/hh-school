package com.kotovdv.hhschool.endless.sequence;

import com.kotovdv.hhschool.endless.sequence.logic.SequenceLocationDetector;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigInteger;

import static com.kotovdv.hhschool.endless.sequence.util.math.BigIntegerMath.bigInteger;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Dmitriy Kotov
 */
public class SequenceLocationDetectorTest {


    @DataProvider(parallel = true)
    public static Object[][] dataProvider() {
        StringBuilder stringBuilder = new StringBuilder();

        int maxValue = 10_000;
        for (int i = 1; i <= maxValue; i++) {
            stringBuilder.append(Integer.toString(i));
        }

        Object[][] args = new Object[maxValue][];
        for (int i = 1; i <= maxValue; i++) {
            String sequence = Integer.toString(i);
            int firstIndex = stringBuilder.indexOf(sequence);
            args[i - 1] = new Object[]{
                    sequence, bigInteger(firstIndex + 1)
            };
        }

        return args;
    }

    @Test(dataProvider = "dataProvider")
    public void testGetStartingIndexOf(String sequence, BigInteger expectedIndex) throws Exception {
        SequenceLocationDetector sequenceLocationDetector = new SequenceLocationDetector();
        BigInteger actualIndex = sequenceLocationDetector.findIndexOf(sequence);

        assertThat(actualIndex).isEqualTo(expectedIndex);
    }


}