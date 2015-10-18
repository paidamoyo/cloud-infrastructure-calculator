package com.calculator.application.service;

public class InstanceStateProcessorException extends RuntimeException {
    public InstanceStateProcessorException(String message, Exception exception) {
        super(message, exception);

    }

    public InstanceStateProcessorException(String message) {
        super(message);
    }
}
