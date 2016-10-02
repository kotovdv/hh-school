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
        int limit = 5000;
        Object[][] args = new Object[limit][];
        for (int i = 0, currentIndex = 0; i < limit; i++) {
            String strValue = Integer.toString(i + 1);
            currentIndex += strValue.length();
            args[i] = new Object[]{
                    i + 1, currentIndex - strValue.length() + 1
            };

        }


        return args;

    }


    @Test(dataProvider = "dataProvider")
    public void testGetIndexOf(int number, int expectedIndex) throws Exception {
        EndlessSequenceLogic endlessSequenceLogic = new EndlessSequenceLogic();
        int actualIndex = endlessSequenceLogic.getIndexOf(number);
        assertThat(actualIndex).isEqualTo(expectedIndex);
    }

}