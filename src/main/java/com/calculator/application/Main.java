package com.calculator.application;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.calculator.application.service.CustomerBuilder;
import com.calculator.application.service.filereader.CloudInstanceStateReader;
import com.calculator.application.service.filereader.HostStateReader;
import com.calculator.application.view.CloudInfrastructureOutput;
import com.calculator.domain.CloudInstance;
import com.calculator.domain.Customer;
import com.calculator.domain.Host;
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

