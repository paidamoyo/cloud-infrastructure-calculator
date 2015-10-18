package com.calculator.application;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.calculator.application.service.CloudInstanceStateProcessor;
import com.calculator.application.service.CustomerProcessor;
import com.calculator.application.view.CloudInfrastructureOutput;
import com.calculator.domain.CloudInstance;
import com.calculator.domain.Customer;

public class Main {

    public static void main(String[] args) {
        System.out.println("app is running!" + "\n");

        Path pathInstanceState = Paths.get(args[0]);
        Path pathHostState = Paths.get(args[1]);

        List<CloudInstance> cloudInstances = new CloudInstanceStateProcessor(pathInstanceState).process();

        List<Customer> customers = new CustomerProcessor(cloudInstances).getCustomers();
        StringBuilder results = new CloudInfrastructureOutput(customers).display();

        System.out.println("results:" + "\n");
        System.out.println(results);

    }


}

