package com.calculator.application.service.fileprocessor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.calculator.application.view.HostClustering;
import com.calculator.domain.CloudInstance;
import com.calculator.domain.Customer;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class HostClusteringTest {

    @Test
    public void shouldReturnAMapOfCustomerMaxNumberOfFleetPerHost() throws Exception {

        //given
        CloudInstance instanceOne = CloudInstance.from("1", "8", "2");
        CloudInstance instanceTwo = CloudInstance.from("2", "8", "2");
        CloudInstance instanceThree = CloudInstance.from("3", "8", "2");
        CloudInstance instanceFour = CloudInstance.from("4", "8", "7");
        CloudInstance instanceTwelve = CloudInstance.from("12", "8", "6");

        CloudInstance instanceEight = CloudInstance.from("8", "9", "3");
        CloudInstance instanceFourteen = CloudInstance.from("14", "9", "9");
        CloudInstance instanceFifteen = CloudInstance.from("15", "9", "7");

        CloudInstance instanceSeven = CloudInstance.from("7", "13", "9");
        CloudInstance instanceNine = CloudInstance.from("9", "13", "3");

        CloudInstance instanceFive = CloudInstance.from("5", "15", "7");
        CloudInstance instanceEleven = CloudInstance.from("11", "15", "8");

        CloudInstance instanceSix = CloudInstance.from("6", "16", "9");
        CloudInstance instanceTen = CloudInstance.from("10", "16", "5");
        CloudInstance instanceThirteen = CloudInstance.from("13", "16", "8");

        Customer customerEight = Customer.from("8", Arrays.asList(instanceOne, instanceTwo, instanceThree, instanceFour, instanceTwelve));
        Customer customerNine = Customer.from("9", Arrays.asList(instanceEight, instanceFourteen, instanceFifteen));
        Customer customerThirteen = Customer.from("13", Arrays.asList(instanceSeven, instanceNine));
        Customer customerFifteen = Customer.from("15", Arrays.asList(instanceFive, instanceEleven));
        Customer customerSixteen = Customer.from("16", Arrays.asList(instanceSix, instanceTen, instanceThirteen));

        List<Customer> customers = Arrays.asList(customerThirteen, customerFifteen, customerSixteen, customerEight, customerNine);
        HostClustering hostClustering = new HostClustering(customers);

        //when
        Map.Entry<Customer, Double> customerMaxFleetOnHost = hostClustering.customerMaximumOfFleetPerHost();

        //then
        assertThat(customerMaxFleetOnHost.getKey(), is(customerEight));
        assertThat(customerMaxFleetOnHost.getValue(), is(0.6));
    }
}