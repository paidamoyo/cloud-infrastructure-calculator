package com.calculator.application.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.calculator.domain.CloudInstance;
import com.calculator.domain.Customer;
import com.calculator.domain.Host;
import com.google.common.collect.FluentIterable;

public class DataCenterClustering {

    private List<Customer> customers;
    private List<Host> hosts;

    public DataCenterClustering(List<Customer> customers, List<Host> hosts) {
        this.customers = customers;
        this.hosts = hosts;
    }


    public Map.Entry<Customer, Double> customerMaximumOfFleetPerDataCenter() {
        Map<Customer, Map<String, Integer>> dataCenterClustering = new HashMap<>();

        this.customers.forEach(customer ->
                customer.getCloudInstances()
                        .stream()
                        .collect(Collectors.groupingBy(CloudInstance::getHostId))
                        .entrySet()
                        .forEach(hostCloudInstancesMap -> {
                            List<CloudInstance> cloudInstances = hostCloudInstancesMap.getValue();
                            String hostId = hostCloudInstancesMap.getKey();
                            Host host = FluentIterable.from(hosts).firstMatch(input -> input.getId().equals(hostId)).get();

                            final String dataCenterId = host.getDataCenterId();
                            final int size = cloudInstances.size();

                            buildDataCenterClusteringMap(customer, dataCenterId, size, dataCenterClustering);
                        }));


        Map<Customer, Integer> customerMaxFleetMap = findCustomerMaxFleetMap(dataCenterClustering);
        return findCustomerWithMaximumFleetPerCenter(customerMaxFleetMap);

    }

    private Map.Entry<Customer, Double> findCustomerWithMaximumFleetPerCenter(Map<Customer, Integer> customerMaxFleetMap) {
        final Map.Entry<Customer, Integer> customerIntegerEntry = customerMaxFleetMap
                .entrySet()
                .stream()
                .max((entryOne, entryTwo) -> {
                    final double fracOne = entryOne.getValue() / (double) entryOne.getKey().getCloudInstances().size();
                    final double fracTwo = entryTwo.getValue() / (double) entryTwo.getKey().getCloudInstances().size();
                    return fracOne > fracTwo ? 1 : -1;
                })
                .get();
        return new Map.Entry<Customer, Double>() {
            @Override
            public Customer getKey() {
                return customerIntegerEntry.getKey();
            }

            @Override
            public Double getValue() {
                return customerIntegerEntry.getValue() / (double) customerIntegerEntry.getKey().getCloudInstances().size();
            }

            @Override
            public Double setValue(Double value) {
                return null;
            }
        };
    }

    private Map<Customer, Integer> findCustomerMaxFleetMap(Map<Customer, Map<String, Integer>> dataCenterClustering) {
        Map<Customer, Integer> customerMaxFleetMap = new HashMap<>();
        dataCenterClustering.entrySet().forEach(customerMapEntry -> {
            final Integer maxNumberOfInstances = customerMapEntry
                    .getValue()
                    .entrySet()
                    .stream()
                    .max((entryOne, entryTwo) -> entryOne.getValue().compareTo(entryTwo.getValue())).get().getValue();
            customerMaxFleetMap.put(customerMapEntry.getKey(), maxNumberOfInstances);
        });
        return customerMaxFleetMap;
    }

    private Map<Customer, Map<String, Integer>> buildDataCenterClusteringMap(Customer customer, final String dataCenterId, final int size, Map<Customer, Map<String, Integer>> dataCenterClustering) {

        Map<String, Integer> dataCenterIdPerNumberOfInstances = new HashMap<String, Integer>() {{
            put(dataCenterId, size);
        }};

        if (dataCenterClustering.containsKey(customer)) {
            updateDataCenterMap(dataCenterId, size, dataCenterClustering.get(customer));

        } else {
            dataCenterClustering.put(customer, dataCenterIdPerNumberOfInstances);
        }

        return dataCenterClustering;
    }

    private void updateDataCenterMap(String dataCenterId, int size, Map<String, Integer> customerMap) {

        if (customerMap.containsKey(dataCenterId)) {
            customerMap.put(dataCenterId, customerMap.get(dataCenterId) + size);

        } else {
            customerMap.put(dataCenterId, size);
        }
    }


}
