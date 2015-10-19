package com.statistics.application.view;

import java.util.List;
import java.util.Map;

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

        statisticsFileWriter.writeToFile(result);

        return result;
    }

}
