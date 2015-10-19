package com.statistics.domain;

import java.util.List;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Customer {

    private final String id;
    private final List<CloudInstance> cloudInstances;

    public static Customer from(String id, List<CloudInstance> cloudInstances) {
        return new Customer(id, cloudInstances);
    }
}
