package data.provider;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitriy Kotov
 */
public class InputOutputFactory {

    private static final int INPUT_CELL = 0;
    private static final int OUTPUT_CELL = 1;
    private final DataFormatter formatter = new DataFormatter();

    public Object[][] create(String url, String sheetName) {
        List<InputOutput> inputOutputs = gather(url, sheetName);

        Object[][] objects = new Object[inputOutputs.size()][1];
        for (int i = 0; i < inputOutputs.size(); i++) {
            InputOutput inputOutput = inputOutputs.get(i);
            objects[i] = new Object[]{
                    inputOutput.getInput(), inputOutput.getOutput()
            };
        }

        return objects;
    }


    private List<InputOutput> gather(String url, String sheetName) {
        List<InputOutput> inputOutputs = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(new URL(url).openStream())) {
            Sheet scenarios = workbook.getSheet(sheetName);

            scenarios.forEach(currentRow -> {
                if (!isHeader(currentRow)) {
                    String input = getCellValue(currentRow, INPUT_CELL);
                    String output = getCellValue(currentRow, OUTPUT_CELL);
                    inputOutputs.add(new InputOutput(input, output));
                }
            });

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return inputOutputs;
    }

    private String getCellValue(Row row, int cellIndex) {
        return formatter.formatCellValue(row.getCell(cellIndex));
    }

    private boolean isHeader(Row currentRow) {
        return currentRow.getRowNum() == 0;
    }

}