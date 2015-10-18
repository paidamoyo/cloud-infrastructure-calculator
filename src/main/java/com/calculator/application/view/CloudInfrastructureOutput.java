package com.calculator.application.view;

import java.util.List;
import java.util.Map;

import com.calculator.domain.Customer;

public class CloudInfrastructureOutput {

    private List<Customer> customers;

    public CloudInfrastructureOutput(List<Customer> customers) {
        this.customers = customers;
    }


    public StringBuilder display() {
        Map.Entry<Customer, Double> maximumOfFleetPerHost = new HostClustering(customers).customerMaximumOfFleetPerHost();

        StringBuilder result = new StringBuilder();
        result.append("HostClustering:").append(maximumOfFleetPerHost.getKey().getId())
                .append(",").append(maximumOfFleetPerHost.getValue());
        return result;
    }
}
