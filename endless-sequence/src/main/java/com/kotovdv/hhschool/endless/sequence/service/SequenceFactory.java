package com.kotovdv.hhschool.endless.sequence.service;


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


    public static void main(String[] args) {
        int i = 1;
        for (int j = 1; j < 1000; j++) {

            String strPresentation = Integer.toString(j);
            for (int k = 0; k < strPresentation.length(); k++) {
                if (k == 0) {
                    System.out.printf("[%d,%s] NUMBER [" + j + "]%n", i++, strPresentation.charAt(k));
                } else {
                    System.out.printf("[%d,%s]%n", i++, strPresentation.charAt(k));

                }
            }


        }
    }

}
