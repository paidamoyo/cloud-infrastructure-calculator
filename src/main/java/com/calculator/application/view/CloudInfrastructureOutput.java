package com.calculator.application.view;

import java.util.List;
import java.util.Map;

import com.calculator.domain.Customer;
import com.calculator.domain.Host;
import com.google.common.collect.FluentIterable;

public class CloudInfrastructureOutput {

    private List<Customer> customers;
    private List<Host> hosts;

    public CloudInfrastructureOutput(List<Customer> customers, List<Host> hosts) {
        this.customers = customers;
        this.hosts = hosts;
    }


    public StringBuilder display() {
        Map.Entry<Customer, Double> maximumOfFleetPerHost = new HostClustering(customers).customerMaximumOfFleetPerHost();
        Map.Entry<Customer, Double> maximumOfFleetPerCenter = new DataCenterClustering(customers, hosts).customerMaximumOfFleetPerDataCenter();

        final FluentIterable<Host> hostsWithEmptySlots = FluentIterable.from(hosts).filter(Host::hasEmptySlot);

        StringBuilder result = new StringBuilder();
        result.append("HostClustering:").append(maximumOfFleetPerHost.getKey().getId())
                .append(",").append(maximumOfFleetPerHost.getValue()).append("\n");
        result.append("DatacentreClustering:").append(maximumOfFleetPerCenter.getKey().getId())
                .append(",").append(maximumOfFleetPerCenter.getValue());
        result.append("AvailableHosts:");

        hostsWithEmptySlots.forEach(host -> result.append(host.getId()).append(","));
        result.append("\n");
        return result;
    }
}
