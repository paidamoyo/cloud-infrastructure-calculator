package com.statistics.application;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.statistics.application.service.CustomerBuilder;
import com.statistics.application.service.filereader.CloudInstanceStateReader;
import com.statistics.application.service.filereader.HostStateReader;
import com.statistics.application.view.CloudInfrastructureOutput;
import com.statistics.domain.CloudInstance;
import com.statistics.domain.Customer;
import com.statistics.domain.Host;
import com.google.common.collect.ImmutableList;

public class Main {

    public static void main(String[] args) {
        System.out.println("app is running!" + "\n");

        Path pathInstanceState = Paths.get(args[0]);
        Path pathHostState = Paths.get(args[1]);

        ImmutableList<CloudInstance> cloudInstances = new CloudInstanceStateReader(pathInstanceState).process();
        ImmutableList<Host> hosts = new HostStateReader(pathHostState, cloudInstances).process();

        ImmutableList<Customer> customers = new CustomerBuilder(cloudInstances).create();
        StringBuilder results = new CloudInfrastructureOutput(customers, hosts).display();

        System.out.println("results:" + "\n");
        System.out.println(results);

    }


}

