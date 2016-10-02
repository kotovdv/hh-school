package com.kotovdv.hhschool.endless.sequence;

import org.testng.annotations.Test;

/**
 * @author Dmitriy Kotov
 */
public class EndlessSequenceTest {

    @Test(dataProviderClass = EndlessSequenceDataProvider.class, dataProvider = "dataProvider")
    public void testSolve() throws Exception {

    }

}