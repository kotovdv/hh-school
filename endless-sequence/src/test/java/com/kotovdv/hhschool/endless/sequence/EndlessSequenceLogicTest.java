package com.kotovdv.hhschool.endless.sequence;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Dmitriy Kotov
 */
public class EndlessSequenceLogicTest {


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
            System.out.println(i);
            args[i - 1] = new Object[]{
                    sequence, firstIndex + 1
            };
        }

        return args;

    }


    @Test(dataProvider = "dataProvider")
    public void testGetStartingIndexOf(String sequence, int expectedIndex) throws Exception {
        EndlessSequenceLogic endlessSequenceLogic = new EndlessSequenceLogic();
        int actualIndex = endlessSequenceLogic.getStartingIndexOf(sequence);

        assertThat(actualIndex).isEqualTo(expectedIndex);
    }




}