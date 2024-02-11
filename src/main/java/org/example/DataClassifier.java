package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataClassifier {
    void classifyData(String line, Map<String, List<String>> data) {
        // Trim leading and trailing whitespace
        line = line.trim();

        // Check if the line represents an integer
        try {
            Integer.parseInt(line);
            addToDataMap("integers", line, data);
            return; // Exit method if the line is an integer
        } catch (NumberFormatException e) {
            // Not an integer
        }

        // Check if the line represents a float
        try {
            Float.parseFloat(line);
            addToDataMap("floats", line, data);
            return; // Exit method if the line is a float
        } catch (NumberFormatException e) {
            // Not a float
        }

        // Otherwise, treat the line as a string
        addToDataMap("strings", line, data);
    }

    private void addToDataMap(String dataType, String value, Map<String, List<String>> data) {
        // Add the value to the appropriate list in the data map
        List<String> dataList = data.computeIfAbsent(dataType, k -> new ArrayList<>());
        dataList.add(value);
    }
}
