package com.statistics.application.view;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class StatisticsFileWriter {

    public void writeToFile(String content) {
        try {
            new OutputStreamWriter(new FileOutputStream("Statistics.txt"), "UTF-8").write(content);
        } catch (Exception exception) {
            String message = String.format("Error writing to statistics file for content:%s", content);
            System.out.println(message);
            throw new StatisticsFileWriterException(message, exception);
        }
    }
}
