package org.example;

public class Main {
    public static void main(String[] args) {
        try {
            FileParser fileParser = new FileParser();
            fileParser.processArguments(args);
            fileParser.filterAndWriteFiles();
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
