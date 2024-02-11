package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class FileParser {
  private List<String> inputFiles;
  private String outputDirectory;
  private String outputFilePrefix;
  private boolean appendMode;
  private boolean summarize;
  private boolean fullStatistics;

  public FileParser() {
    this.inputFiles = new ArrayList<>();
    this.outputDirectory = "./";
    this.outputFilePrefix = "";
    this.appendMode = false;
    this.summarize = false;
    this.fullStatistics = false;
  }

  public void processArguments(String[] args) throws IllegalArgumentException {
    for (int i = 0; i < args.length; i++) {
      String arg = args[i];
      if (arg.equals("-o") || arg.equals("--output-directory")) {
        if (i + 1 < args.length) {
          outputDirectory = args[i + 1];
          i++; // Skip the next argument
        } else {
          throw new IllegalArgumentException("Output directory not specified");
        }
      } else if (arg.equals("-p") || arg.equals("--output-prefix")) {
        if (i + 1 < args.length) {
          outputFilePrefix = args[i + 1];
          i++; // Skip the next argument
        } else {
          throw new IllegalArgumentException("Output file prefix not specified");
        }
      } else if (arg.equals("-a") || arg.equals("--append")) {
        appendMode = true;
      } else if (arg.equals("-s") || arg.equals("--summarize")) {
        summarize = true;
      } else if (arg.equals("-f") || arg.equals("--full-statistics")) {
        fullStatistics = true;
      } else {
        // Assume it's an input file
        inputFiles.add(arg);
      }
    }
  }

  public void filterAndWriteFiles() throws IOException {
    Map<String, List<String>> data = new HashMap<>();

    // Process each input file
    for (String inputFile : inputFiles) {
      File file = new File(inputFile);
      if (!file.exists() || !file.isFile()) {
        System.err.println("Error: Input file '" + inputFile + "' does not exist or is not a regular file.");
        continue;
      }

      processFile(file, data);
    }

    // Write data to output files
    for (Map.Entry<String, List<String>> entry : data.entrySet()) {
      String dataType = entry.getKey();
      List<String> values = entry.getValue();

      if (values.isEmpty()) {
        continue; // Skip empty lists
      }

      String outputFileName = outputDirectory + File.separator + outputFilePrefix + dataType + ".txt";
      writeOutput(values, outputFileName);
    }
  }

  private void processFile(File inputFile, Map<String, List<String>> data) throws IOException {
    // Read and process each line of the input file
    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
      String line;
      while ((line = reader.readLine()) != null) {
        classifyData(line, data);
      }
    }
  }

  private void writeOutput(List<String> values, String outputFileName) throws IOException {
    // Write data to the output file
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName, appendMode))) {
      for (String value : values) {
        writer.write(value);
        writer.newLine();
      }
    }
  }

  private void classifyData(String line, Map<String, List<String>> data) {
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

  private void printStatistics(Map<String, List<String>> data) {
    for (Map.Entry<String, List<String>> entry : data.entrySet()) {
      String dataType = entry.getKey();
      List<String> values = entry.getValue();

      System.out.println("Statistics for " + dataType + ":");

      // Print the number of elements
      System.out.println("Number of elements: " + values.size());

      if (summarize || fullStatistics) {
        // Calculate and print additional statistics if needed
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
