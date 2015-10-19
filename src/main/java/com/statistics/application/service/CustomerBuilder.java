package com.statistics.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.statistics.domain.CloudInstance;
import com.statistics.domain.Customer;
import com.google.common.collect.ImmutableList;

public class CustomerBuilder {

    private ImmutableList<CloudInstance> cloudInstances;

    public CustomerBuilder(List<CloudInstance> cloudInstances) {

        this.cloudInstances = ImmutableList.copyOf(cloudInstances);
    }

    public ImmutableList<Customer> create() {
        List<Customer> customers = new ArrayList<>();
        groupByCustomerId()
                .entrySet()
                .forEach(
                        entry -> {
                            Customer customer = Customer.from(entry.getKey(), entry.getValue());
                            customers.add(customer);
                        }
                );


        return ImmutableList.copyOf(customers);
    }

    private Map<String, List<CloudInstance>> groupByCustomerId() {
        return this.cloudInstances
                .stream()
                .collect(Collectors.groupingBy(CloudInstance::getCustomerId));


    }


}
