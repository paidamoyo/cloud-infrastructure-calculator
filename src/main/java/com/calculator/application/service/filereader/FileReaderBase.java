package com.calculator.application.service.filereader;

import java.util.List;

public interface FileReaderBase<T> {

    List<T> process();

}
