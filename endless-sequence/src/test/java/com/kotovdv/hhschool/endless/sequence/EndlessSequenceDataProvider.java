package com.kotovdv.hhschool.endless.sequence;

import com.kotovdv.hhschool.tests.common.InputOutputFactory;
import org.testng.annotations.DataProvider;

/**
 * @author Dmitriy Kotov
 */
public class EndlessSequenceDataProvider {

    private static final String TEST_SAMPLES_URL = "https://docs.google.com/spreadsheets/d/1cVIr-VTsfJJf-J7jwwqD-nKT6p516iV3stDr2n27LhQ/pub?output=xlsx";
    private static final String SHEET_NAME = "endless_sequence";

    @DataProvider
    public static Object[][] dataProvider() {
        return new InputOutputFactory().create(TEST_SAMPLES_URL, SHEET_NAME);
    }
}
