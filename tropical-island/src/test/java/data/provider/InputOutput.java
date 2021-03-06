package data.provider;

/**
 * @author Dmitriy Kotov
 */
public class InputOutput {
    private final String input;
    private final String output;

    InputOutput(String input, String output) {
        this.input = input;
        this.output = output;
    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }
}
