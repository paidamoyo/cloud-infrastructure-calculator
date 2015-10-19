package com.statistics.application.service.filereader;

public class HostStateReaderException extends RuntimeException {
    public HostStateReaderException(String message, Exception exception) {
        super(message, exception);
    }

    public HostStateReaderException(String message) {
        super(message);
    }
}
