package com.statistics.application.view;

public class StatisticsFileWriterException extends RuntimeException {
    public StatisticsFileWriterException(String message, Exception exception) {
        super(message, exception);
    }
}
