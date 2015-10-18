package com.calculator.application.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.calculator.domain.CloudInstance;
import com.calculator.domain.Customer;

public class HostClustering {

    private List<Customer> customers;

    public HostClustering(List<Customer> customers) {
        this.customers = customers;
    }


    public Map<Customer, Integer> customerMaximumOfFleetPerHost() {

        Map<Customer, Integer> hostClustering = new HashMap<>();

        this.customers.forEach(customer -> {
            customer.getCloudInstances()
                    .stream()
                    .collect(Collectors.groupingBy(CloudInstance::getHostId))
                    .entrySet()
                    .forEach(hostIdCloudInstancesMap -> {
                        Integer numberOfInstancesInHost = hostIdCloudInstancesMap.getValue().size();
                        if (hostClustering.containsKey(customer)) {
                            Integer currentMaxInstancesInHost = hostClustering.get(customer);
                            Integer maxInstancesInHost = numberOfInstancesInHost > currentMaxInstancesInHost ?
                                    numberOfInstancesInHost : currentMaxInstancesInHost;

                            hostClustering.put(customer, maxInstancesInHost);

                        } else {
                            hostClustering.put(customer, numberOfInstancesInHost);
                        }
                    });
        });
        return hostClustering;
    }
}
