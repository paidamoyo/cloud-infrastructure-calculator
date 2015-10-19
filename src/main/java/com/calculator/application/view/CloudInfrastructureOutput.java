package com.calculator.application.view;

import java.util.List;
import java.util.Map;

import com.calculator.application.service.statistics.DataCenterClusteringStatistics;
import com.calculator.application.service.statistics.HostClusteringStatistics;
import com.calculator.domain.Customer;
import com.calculator.domain.Host;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;

public class CloudInfrastructureOutput {

    private ImmutableList<Customer> customers;
    private ImmutableList<Host> hosts;

    public CloudInfrastructureOutput(List<Customer> customers, List<Host> hosts) {
        this.customers = ImmutableList.copyOf(customers);
        this.hosts = ImmutableList.copyOf(hosts);
    }


    public StringBuilder display() {
        Map.Entry<Customer, Double> maximumOfFleetPerHost = new HostClusteringStatistics(customers).customerMaximumOfFleetPerHost();
        Map.Entry<Customer, Double> maximumOfFleetPerCenter = new DataCenterClusteringStatistics(customers, hosts).customerMaximumOfFleetPerDataCenter();

        final FluentIterable<Host> hostsWithEmptySlots = FluentIterable.from(hosts).filter(Host::hasEmptySlot);

        StringBuilder result = new StringBuilder();
        result.append("HostClustering:").append(maximumOfFleetPerHost.getKey().getId())
                .append(",").append(maximumOfFleetPerHost.getValue()).append("\n");
        result.append("DatacentreClustering:").append(maximumOfFleetPerCenter.getKey().getId())
                .append(",").append(maximumOfFleetPerCenter.getValue()).append("\n");
        result.append("AvailableHosts:");

        hostsWithEmptySlots.forEach(host -> result.append(host.getId()).append(","));
        result.append("\n");
        return result;
    }
}
