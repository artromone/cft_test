package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataClassifier {
    void classifyData(String line, Map<String, List<String>> data) {
        line = line.trim();

        try {
            Integer.parseInt(line);
            addToDataMap("integers", line, data);
            return; // Exit if is an integer
        } catch (NumberFormatException e) {
            // Not an integer
        }

        try {
            Float.parseFloat(line);
            addToDataMap("floats", line, data);
            return; // Exit if is a float
        } catch (NumberFormatException e) {
            // Not a float
        }

        // Otherwise, treat the line as a string
        addToDataMap("strings", line, data);
    }

    private void addToDataMap(String dataType, String value, Map<String, List<String>> data) {
        List<String> dataList = data.computeIfAbsent(dataType, k -> new ArrayList<>());
        dataList.add(value);
    }
}
