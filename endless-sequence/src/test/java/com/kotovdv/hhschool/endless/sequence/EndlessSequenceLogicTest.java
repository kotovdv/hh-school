package com.kotovdv.hhschool.endless.sequence;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Dmitriy Kotov
 */
public class EndlessSequenceLogicTest {


    @DataProvider
    public static Object[][] dataProvider() {
        StringBuilder stringBuilder = new StringBuilder();

        int MAX_VALUE = 5000;
        for (int i = 1; i <= MAX_VALUE; i++) {
            stringBuilder.append(Integer.toString(i));
        }

        Object[][] args = new Object[MAX_VALUE][];
        for (int i = 1; i <= MAX_VALUE; i++) {
            String sequence = Integer.toString(i);
            int firstIndex = stringBuilder.indexOf(sequence);
            args[i - 1] = new Object[]{
                    sequence, firstIndex + 1
            };
        }

        return args;

    }


    @Test(dataProvider = "dataProvider")
    public void testGetIndexOf(String sequence, int expectedIndex) throws Exception {
        EndlessSequenceLogic endlessSequenceLogic = new EndlessSequenceLogic();
        int actualIndex = endlessSequenceLogic.getStartingIndexOf(sequence);

        assertThat(actualIndex).isEqualTo(expectedIndex);
    }

}