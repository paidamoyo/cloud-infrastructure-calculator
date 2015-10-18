package com.calculator.application.service;

public class CloudInstanceStateProcessorException extends RuntimeException {
    public CloudInstanceStateProcessorException(String message, Exception exception) {
        super(message, exception);

    }

    public CloudInstanceStateProcessorException(String message) {
        super(message);
    }
}
