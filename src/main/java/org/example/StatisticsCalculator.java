package org.example;

import java.util.List;
import java.util.Map;

class StatisticsCalculator {
    private final boolean isFull;

    public StatisticsCalculator(boolean isFull) {
        this.isFull = isFull;
    }

    public void printStatistics(Map<String, List<String>> data) {
        for (Map.Entry<String, List<String>> entry : data.entrySet()) {
            String dataType = entry.getKey();
            List<String> values = entry.getValue();

            System.out.println("Statistics for " + dataType + ": Number of elements: " + values.size());

            if (isFull) {
                switch (dataType) {
                    case "integers":
                        printIntegerStatistics(values);
                        break;
                    case "floats":
                        printFloatStatistics(values);
                        break;
                    case "strings":
                        printStringStatistics(values);
                        break;
                }
            }
        }
    }

    private void printIntegerStatistics(List<String> values) {
        printNumberStatistics(values, Integer.class);
    }

    private void printFloatStatistics(List<String> values) {
        printNumberStatistics(values, Float.class);
    }

    private void printStringStatistics(List<String> values) {
        int minLength = Integer.MAX_VALUE;
        int maxLength = Integer.MIN_VALUE;
        for (String value : values) {
            int length = value.length();
            minLength = Math.min(minLength, length);
            maxLength = Math.max(maxLength, length);
        }

        System.out.println("Min length: " + minLength);
        System.out.println("Max length: " + maxLength);
    }

    private <T extends Number> void printNumberStatistics(List<String> values, Class<T> type) {
        if (values.isEmpty()) {
            return;
        }

        T min = null;
        T max = null;
        double sum = 0;

        for (String value : values) {
            T numberValue = parseValue(value, type);

            if (min == null || numberValue.doubleValue() < min.doubleValue()) {
                min = numberValue;
            }
            if (max == null || numberValue.doubleValue() > max.doubleValue()) {
                max = numberValue;
            }

            sum += numberValue.doubleValue();
        }

        double average = sum / values.size();

        System.out.println("Min: " + min);
        System.out.println("Max: " + max);
        System.out.println("Sum: " + sum);
        System.out.println("Average: " + average);
    }

    private <T extends Number> T parseValue(String value, Class<T> type) {
        try {
            if (type == Integer.class) {
                return (T) Integer.valueOf(value);
            } else if (type == Float.class) {
                return (T) Float.valueOf(value);
            } else {
                throw new IllegalArgumentException("Unsupported number type: " + type.getSimpleName());
            }
        } catch (NumberFormatException e) {
            System.err.println("Error parsing value: " + value + " as " + type.getSimpleName() + ". Skipping...");
            return null;
        }
    }
}