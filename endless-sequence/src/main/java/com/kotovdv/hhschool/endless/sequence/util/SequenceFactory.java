package com.kotovdv.hhschool.endless.sequence.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitriy Kotov
 */
public class SequenceFactory {

    public static List<String> create(InputStream inputStream) {
        List<String> sequences = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                if (currentLine.trim().equals("")) {
                    break;
                }
                sequences.add(currentLine);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return sequences;
    }

}
