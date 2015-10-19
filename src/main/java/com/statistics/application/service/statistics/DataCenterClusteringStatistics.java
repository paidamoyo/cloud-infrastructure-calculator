package com.statistics.application.service.statistics;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.collect.Collections2;
import com.google.common.collect.FluentIterable;
import com.statistics.domain.CloudInstance;
import com.statistics.domain.Customer;
import com.statistics.domain.Host;

public class DataCenterClusteringStatistics {

    private List<Customer> customers;
    private List<Host> hosts;

    public DataCenterClusteringStatistics(List<Customer> customers, List<Host> hosts) {
        this.customers = customers;
        this.hosts = hosts;
    }


    public Collection<Map.Entry<Customer, Double>> customerMaximumOfFleetPerDataCenter() {
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

                            if (cloudInstances.size() > 0) {
                                buildDataCenterClusteringMap(customer, dataCenterId, cloudInstances.size(), dataCenterClustering);
                            }
                        }));


        Collection<Map.Entry<Customer, Double>> customerMaxFleetMap = Collections2
                .transform(findCustomerMaxFleetMap(dataCenterClustering).entrySet(), input ->
                        new AbstractMap.SimpleEntry<>(
                                input.getKey(), input.getValue() / (double) input.getKey().getCloudInstances().size()
                        ));

        final Double maximumFleetPerCenter = maximumFleetPerCenter(customerMaxFleetMap);

        return customersWithMaxFleetPerCenter(customerMaxFleetMap, maximumFleetPerCenter);

    }

    private Collection<Map.Entry<Customer, Double>> customersWithMaxFleetPerCenter(final Collection<Map.Entry<Customer, Double>> customerMaxFleetMap, Double maximumFleetPerCenter) {

        return Collections2.filter(customerMaxFleetMap, input ->
                        input.getValue().equals(maximumFleetPerCenter)
        );

    }

    private Double maximumFleetPerCenter(Collection<Map.Entry<Customer, Double>> customerMaxFleetMap) {
        return customerMaxFleetMap
                .stream()
                .max((entryOne, entryTwo) -> entryOne.getValue().compareTo(entryTwo.getValue()))
                .get().getValue();
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

    private Map<Customer, Map<String, Integer>> buildDataCenterClusteringMap(Customer customer, final String dataCenterId, final int numberOfInstances, Map<Customer, Map<String, Integer>> dataCenterClustering) {

        if (dataCenterClustering.containsKey(customer)) {
            updateCustomerInstancesDataCenterMap(dataCenterId, numberOfInstances, dataCenterClustering.get(customer));

        } else {
            dataCenterClustering.put(customer, new HashMap<String, Integer>() {{
                put(dataCenterId, numberOfInstances);
            }});
        }

        return dataCenterClustering;
    }

    private void updateCustomerInstancesDataCenterMap(String dataCenterId, int numberOfInstances, Map<String, Integer> dataCenterIdInstancesMap) {

        if (dataCenterIdInstancesMap.containsKey(dataCenterId)) {
            dataCenterIdInstancesMap.put(dataCenterId, dataCenterIdInstancesMap.get(dataCenterId) + numberOfInstances);

        } else {
            dataCenterIdInstancesMap.put(dataCenterId, numberOfInstances);
        }
    }


}
