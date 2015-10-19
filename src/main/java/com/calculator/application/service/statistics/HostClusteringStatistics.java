package com.calculator.application.service.statistics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.calculator.domain.CloudInstance;
import com.calculator.domain.Customer;

public class HostClusteringStatistics {

    private List<Customer> customers;

    public HostClusteringStatistics(List<Customer> customers) {
        this.customers = customers;
    }


    public Map.Entry<Customer, Double> customerMaximumOfFleetPerHost() {

        Map<Customer, Double> hostClustering = new HashMap<>();

        this.customers.forEach(customer ->
                customer.getCloudInstances()
                .stream()
                .collect(Collectors.groupingBy(CloudInstance::getHostId))
                .entrySet()
                .forEach(hostIdCloudInstancesMap -> {
                    int totalCustomerCloudInstances = customer.getCloudInstances().size();
                    Double numberOfInstancesInHost = hostIdCloudInstancesMap.getValue().size() / (double) totalCustomerCloudInstances;
                    if (hostClustering.containsKey(customer)) {
                        Double currentMaxInstancesInHost = hostClustering.get(customer);
                        Double maxInstancesInHost = numberOfInstancesInHost > currentMaxInstancesInHost ?
                                numberOfInstancesInHost : currentMaxInstancesInHost;

                        hostClustering.put(customer, maxInstancesInHost);

                    } else {
                        hostClustering.put(customer, numberOfInstancesInHost);
                    }
                }));


        return findCustomerWithMaximumFleetPerHost(hostClustering);
    }

    private Map.Entry<Customer, Double> findCustomerWithMaximumFleetPerHost(Map<Customer, Double> hostClustering) {
        return hostClustering
                .entrySet()
                .stream()
                .max((entryOne, entryTwo) -> entryOne.getValue().compareTo(entryTwo.getValue()))
                .get();

    }
}
