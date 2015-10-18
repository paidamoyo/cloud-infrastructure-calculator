package com.calculator.domain;

import java.util.List;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CloudHost {

    private final String id;
    private final int numberOfSlots;
    private final String dataCenterId;
    private final List<CloudInstance> cloudInstances;

}
