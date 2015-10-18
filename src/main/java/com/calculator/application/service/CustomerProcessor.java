package com.calculator.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.calculator.domain.CloudInstance;
import com.calculator.domain.Customer;

public class CustomerProcessor {

    private List<CloudInstance> cloudInstances;

    public CustomerProcessor(List<CloudInstance> cloudInstances) {

        this.cloudInstances = cloudInstances;
    }

    public List<Customer> getCustomers() {
        List<Customer> customers = new ArrayList<>();
        groupByCustomerId()
                .entrySet()
                .forEach(
                        entry -> {
                            Customer customer = Customer.from(entry.getKey(), entry.getValue());
                            customers.add(customer);
                        }
                );


        return customers;
    }

    private Map<String, List<CloudInstance>> groupByCustomerId() {
        return this.cloudInstances
                .stream()
                .collect(Collectors.groupingBy(CloudInstance::getCustomerId));


    }


}
