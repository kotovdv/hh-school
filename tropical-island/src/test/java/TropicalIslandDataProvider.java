import data.provider.InputOutput;
import data.provider.InputOutputFactory;
import org.testng.annotations.DataProvider;

import java.util.List;

/**
 * @author Dmitriy Kotov
 */
public class TropicalIslandDataProvider {

    private static final String TEST_SAMPLES_URL = "https://docs.google.com/spreadsheets/d/1cVIr-VTsfJJf-J7jwwqD-nKT6p516iV3stDr2n27LhQ/pub?output=xlsx";
    private static final String SHEET_NAME = "scenarios";


    @DataProvider
    public static Object[][] dataProvider() {
        List<InputOutput> inputsAndOutputs = new InputOutputFactory().create(TEST_SAMPLES_URL, SHEET_NAME);

        Object[][] objects = new Object[inputsAndOutputs.size()][1];
        for (int i = 0; i < inputsAndOutputs.size(); i++) {
            InputOutput inputOutput = inputsAndOutputs.get(i);
            objects[i] = new Object[]{
                    inputOutput.getInput(), inputOutput.getOutput()
            };
        }

        return objects;
    }
}