package com.calculator.domain;

import java.util.List;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DataCenter {

    private final String id;
    private final List<Host> hosts;
}
