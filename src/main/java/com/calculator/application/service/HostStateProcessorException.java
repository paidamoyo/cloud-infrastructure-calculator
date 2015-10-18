package com.calculator.application.service;

public class HostStateProcessorException extends RuntimeException {
    public HostStateProcessorException(String message, Exception exception) {
        super(message, exception);
    }

    public HostStateProcessorException(String message) {
        super(message);
    }
}
