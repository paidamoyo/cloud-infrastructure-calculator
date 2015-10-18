package com.calculator.application.service.fileprocessor;

import java.util.List;

public interface FileProcessorBase<T> {

    List<T> process();

}
