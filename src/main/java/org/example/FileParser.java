package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileParser {
    private final List<String> inputFiles;
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
            switch (arg) {
                case "-o":
                case "--output-directory":
                    if (i + 1 < args.length) {
                        outputDirectory = args[i + 1];
                        i++; // Skip next argument
                    } else {
                        throw new IllegalArgumentException("Output directory not specified");
                    }
                    break;
                case "-p":
                case "--output-prefix":
                    if (i + 1 < args.length) {
                        outputFilePrefix = args[i + 1];
                        i++; // Skip next argument
                    } else {
                        throw new IllegalArgumentException("Output file prefix not specified");
                    }
                    break;
                case "-a":
                case "--append":
                    appendMode = true;
                    break;
                case "-s":
                case "--summarize":
                    summarize = true;
                    break;
                case "-f":
                case "--full-statistics":
                    fullStatistics = true;
                    break;
                default:
                    // It is an input file
                    inputFiles.add(arg);
                    break;
            }
        }
    }

    public void filterAndWriteFiles() throws IOException {
        Map<String, List<String>> data = new HashMap<>();

        for (String inputFile : inputFiles) {
            File file = new File(inputFile);
            if (!file.exists() || !file.isFile()) {
                System.err.println("Error: Input file '" + inputFile + "' does not exist or is not a regular file.");
                continue;
            }

            processFile(file, data);
        }

        for (Map.Entry<String, List<String>> entry : data.entrySet()) {
            String dataType = entry.getKey();
            List<String> values = entry.getValue();

            if (values.isEmpty()) {
                continue; // Skip empty lists
            }

            String outputFileName = outputDirectory + File.separator + outputFilePrefix + dataType + ".txt";

            FileWriter fileWriter = new FileWriter();
            fileWriter.writeOutput(values, outputFileName, appendMode);
        }

        if (summarize || fullStatistics) {
            StatisticsCalculator statisticsCalculator = new StatisticsCalculator(fullStatistics);
            statisticsCalculator.printStatistics(data);
        }
    }

    private void processFile(File inputFile, Map<String, List<String>> data) throws IOException {
        // Read and process each line of the input file
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;

            DataClassifier dataClassifier = new DataClassifier();

            while ((line = reader.readLine()) != null) {
                dataClassifier.classifyData(line, data);
            }
        }
    }
}
