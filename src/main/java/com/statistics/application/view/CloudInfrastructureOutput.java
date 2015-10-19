package com.statistics.application.view;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.statistics.application.service.statistics.DataCenterClusteringStatistics;
import com.statistics.application.service.statistics.HostClusteringStatistics;
import com.statistics.domain.Customer;
import com.statistics.domain.Host;

public class CloudInfrastructureOutput {

    private ImmutableList<Customer> customers;
    private ImmutableList<Host> hosts;
    private StatisticsFileWriter statisticsFileWriter;

    public CloudInfrastructureOutput(List<Customer> customers, List<Host> hosts, StatisticsFileWriter statisticsFileWriter) {
        this.statisticsFileWriter = statisticsFileWriter;
        this.customers = ImmutableList.copyOf(customers);
        this.hosts = ImmutableList.copyOf(hosts);
    }


    public StringBuilder display() {
        Set<Map.Entry<Customer, Double>> maximumOfFleetPerHost = new HostClusteringStatistics(customers).customerMaximumOfFleetPerHost();

        Map.Entry<Customer, Double> maximumOfFleetPerCenter = new DataCenterClusteringStatistics(customers, hosts).customerMaximumOfFleetPerDataCenter();

        final FluentIterable<Host> hostsWithEmptySlots = FluentIterable.from(hosts).filter(Host::hasEmptySlot);

        StringBuilder result = new StringBuilder();
        appendHostClusterStatistics(maximumOfFleetPerHost, result);
        appendDataClusterStatistics(maximumOfFleetPerCenter, result);
        appendEmptySlotStatistics(hostsWithEmptySlots, result);
        result.append("\n");

        statisticsFileWriter.writeToFile(result);

        return result;
    }

    private void appendEmptySlotStatistics(FluentIterable<Host> hostsWithEmptySlots, StringBuilder result) {
        result.append("AvailableHosts:");
        hostsWithEmptySlots.forEach(host -> result.append(host.getId()).append(","));
    }

    private void appendDataClusterStatistics(Map.Entry<Customer, Double> maximumOfFleetPerCenter, StringBuilder result) {
        result.append("DatacentreClustering:")
                .append(maximumOfFleetPerCenter.getKey().getId()).append(",")
                .append(maximumOfFleetPerCenter.getValue()).append("\n");
    }

    private void appendHostClusterStatistics(Set<Map.Entry<Customer, Double>> maximumOfFleetPerHost, StringBuilder result) {
        maximumOfFleetPerHost.forEach(customerDoubleEntry ->
                        result.append("HostClustering:")
                                .append(customerDoubleEntry.getKey().getId()).append(",")
                                .append(customerDoubleEntry.getValue()).append("\n")
        );

    }

}
