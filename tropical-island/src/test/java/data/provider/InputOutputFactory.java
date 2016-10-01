package data.provider;

import org.apache.poi.ss.usermodel.*;
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

    public List<InputOutput> create(String url, String sheetName) {
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