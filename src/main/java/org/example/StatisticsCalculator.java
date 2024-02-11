package org.example;

import java.util.List;
import java.util.Map;

class StatisticsCalculator {
  private boolean summarize;
  private boolean fullStatistics;

  public StatisticsCalculator(boolean summarize, boolean fullStatistics) {
    this.summarize = summarize;
    this.fullStatistics = fullStatistics;
  }

  public void printStatistics(Map<String, List<String>> data) {
    if (!summarize && !fullStatistics) {
      return;
    }

    for (Map.Entry<String, List<String>> entry : data.entrySet()) {
      String dataType = entry.getKey();
      List<String> values = entry.getValue();

      System.out.println("Statistics for " + dataType + ": Number of elements: " + values.size());

      if (fullStatistics) {
        if (dataType.equals("integers")) {
          printIntegerStatistics(values);
        } else if (dataType.equals("floats")) {
          printFloatStatistics(values);
        } else if (dataType.equals("strings")) {
          printStringStatistics(values);
        }
      }
    }
  }

  private void printIntegerStatistics(List<String> values) {
    if (fullStatistics) {
      // Calculate additional statistics for integers (min, max, sum, average)
      int min = Integer.MAX_VALUE;
      int max = Integer.MIN_VALUE;
      int sum = 0;
      for (String value : values) {
        int intValue = Integer.parseInt(value);
        min = Math.min(min, intValue);
        max = Math.max(max, intValue);
        sum += intValue;
      }
      double average = (double) sum / values.size();

      System.out.println("Min: " + min);
      System.out.println("Max: " + max);
      System.out.println("Sum: " + sum);
      System.out.println("Average: " + average);
    }
  }

  private void printFloatStatistics(List<String> values) {
    if (fullStatistics) {
      // Calculate additional statistics for floats (min, max, sum, average)
      float min = Float.MAX_VALUE;
      float max = -Float.MAX_VALUE;
      float sum = 0;
      for (String value : values) {
        float floatValue = Float.parseFloat(value);
        min = Math.min(min, floatValue);
        max = Math.max(max, floatValue);
        sum += floatValue;
      }
      float average = sum / values.size();

      System.out.println("Min: " + min);
      System.out.println("Max: " + max);
      System.out.println("Sum: " + sum);
      System.out.println("Average: " + average);
    }
  }

  private void printStringStatistics(List<String> values) {
    if (fullStatistics) {
      // Calculate additional statistics for strings (min length, max length)
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
  }
}