package com.statistics.application.service.statistics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Maps;
import com.statistics.domain.CloudInstance;
import com.statistics.domain.Customer;

public class HostClusteringStatistics {

    private List<Customer> customers;

    public HostClusteringStatistics(List<Customer> customers) {
        this.customers = customers;
    }


    public Set<Map.Entry<Customer, Double>> customerMaximumOfFleetPerHost() {

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


        return findCustomersWithMaximumFleetPerHost(hostClustering, findMaximumFleetPerHost(hostClustering));

    }


    private Set<Map.Entry<Customer, Double>> findCustomersWithMaximumFleetPerHost(Map<Customer, Double> hostClustering, Double maximumFleetPerHost) {

        return Maps.filterEntries(hostClustering, input -> {
            return input.getValue().equals(maximumFleetPerHost);
        }).entrySet();
    }

    private Double findMaximumFleetPerHost(Map<Customer, Double> hostClustering) {
        return hostClustering
                .entrySet()
                .stream()
                .max((entryOne, entryTwo) -> entryOne.getValue().compareTo(entryTwo.getValue()))
                .get()
                .getValue();

    }
}
