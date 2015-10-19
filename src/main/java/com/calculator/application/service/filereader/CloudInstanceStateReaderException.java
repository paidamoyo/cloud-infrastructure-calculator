package com.calculator.application.service.filereader;

public class CloudInstanceStateReaderException extends RuntimeException {
    public CloudInstanceStateReaderException(String message, Exception exception) {
        super(message, exception);

    }

    public CloudInstanceStateReaderException(String message) {
        super(message);
    }
}
