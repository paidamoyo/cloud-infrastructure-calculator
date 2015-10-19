package com.statistics.application.view;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class StatisticsFileWriter {

    public static final String STATISTICS_FILE_NAME = "Statistics.txt";

    public void writeToFile(StringBuilder content) {
        System.out.printf("Writing to file: %s\n%n", STATISTICS_FILE_NAME);
        try {
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(STATISTICS_FILE_NAME), "UTF-8");
            writer.append(content);
            writer.flush();
            writer.close();
        } catch (Exception exception) {
            String message = String.format("Error writing to statistics file for content:%s", content);
            System.out.println(message);
            throw new StatisticsFileWriterException(message, exception);
        }
    }
}
