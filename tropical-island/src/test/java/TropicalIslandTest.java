import com.kotovdv.hhschool.tropical.island.TropicalIsland;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Dmitriy Kotov
 */
public class TropicalIslandTest {


    //TODO DISABLE TEST LATER
    @Test(dataProviderClass = TropicalIslandDataProvider.class, dataProvider = "dataProvider")
    public void testTropicalIslandFlooding(String input, String expectedOutput) throws Exception {
        try (InputStream inputStream = new ByteArrayInputStream(input.getBytes());
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             PrintStream outputStream = new PrintStream(byteArrayOutputStream)) {

            TropicalIsland tropicalIsland = new TropicalIsland();
            tropicalIsland.solve(inputStream, outputStream);

            String actualOutput = byteArrayOutputStream.toString("UTF-8");
            assertThat(getNumbersOnly(expectedOutput)).isEqualTo(getNumbersOnly(actualOutput));
        }
    }

    private String getNumbersOnly(String actualOutput) {
        return actualOutput.replaceAll("[^\\d]", "");
    }
}
