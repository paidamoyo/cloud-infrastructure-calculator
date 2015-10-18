package com.calculator.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.calculator.domain.DataCenter;
import com.calculator.domain.Host;

public class DataCenterBuilder {

    private List<Host> hosts;

    public DataCenterBuilder(List<Host> hosts) {
        this.hosts = hosts;
    }

    public List<DataCenter> create() {
        List<DataCenter> dataCenters = new ArrayList<>();
        groupByDataCenterId()
                .entrySet()
                .forEach(
                        entry -> {
                            DataCenter dataCenter = DataCenter.from(entry.getKey(), entry.getValue());
                            dataCenters.add(dataCenter);
                        }
                );


        return dataCenters;
    }

    private Map<String, List<Host>> groupByDataCenterId() {
        return this.hosts
                .stream()
                .collect(Collectors.groupingBy(Host::getDataCenterId));


    }


}
