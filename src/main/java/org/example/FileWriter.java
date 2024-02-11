package org.example;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

public class FileWriter {
    void writeOutput(List<String> values, String outputFileName, boolean appendMode) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(outputFileName, appendMode))) {
            for (String value : values) {
                writer.write(value);
                writer.newLine();
            }
        }
    }
}
