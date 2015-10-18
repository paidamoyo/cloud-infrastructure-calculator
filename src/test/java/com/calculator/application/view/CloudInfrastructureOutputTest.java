package com.calculator.application.view;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.calculator.domain.CloudInstance;
import com.calculator.domain.Customer;
import com.calculator.domain.Host;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CloudInfrastructureOutputTest {

    @Test
    public void shouldDisplayHostClustering() throws Exception {

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


        Host hostTwo = Host.from("2", 4, "0", Arrays.asList(instanceOne, instanceTwo, instanceThree));
        Host hostFive = Host.from("5", 4, "0", Collections.singletonList(instanceTen));
        Host hostSeven = Host.from("7", 3, "0", Arrays.asList(instanceFour, instanceFive, instanceFifteen));
        Host hostNine = Host.from("9", 3, "1", Arrays.asList(instanceSix, instanceSeven, instanceFourteen));
        Host hostThree = Host.from("3", 3, "1", Arrays.asList(instanceEight, instanceNine));
        Host hostTen = Host.from("10", 2, "2", Collections.emptyList());
        Host hostSix = Host.from("6", 4, "2", Collections.singletonList(instanceTwelve));
        Host hostEight = Host.from("8", 2, "2", Arrays.asList(instanceEleven, instanceThirteen));

        List<Host> hosts = Arrays.asList(hostTwo, hostFive, hostSeven, hostNine, hostThree, hostTen, hostSix,
                hostEight);

        CloudInfrastructureOutput output = new CloudInfrastructureOutput(customers, hosts );

        //when
        StringBuilder display = output.display();

        //then
        StringBuilder expected = new StringBuilder();
        expected.append("HostClustering:").append("8").append(",").append(0.6).append("\n");
        expected.append("DatacentreClustering:").append("13").append(",").append(1.0);
        assertThat(display.toString(), is(expected.toString()));
    }
}