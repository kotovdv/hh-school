package data.provider;

import org.testng.annotations.DataProvider;

/**
 * @author Dmitriy Kotov
 */
public class TropicalIslandDataProvider {

    private static final String TEST_SAMPLES_URL = "https://docs.google.com/spreadsheets/d/1cVIr-VTsfJJf-J7jwwqD-nKT6p516iV3stDr2n27LhQ/pub?output=xlsx";
    private static final String SHEET_NAME = "tropical_island";


    @DataProvider
    public static Object[][] dataProvider() {
        return new InputOutputFactory().create(TEST_SAMPLES_URL, SHEET_NAME);
    }
}