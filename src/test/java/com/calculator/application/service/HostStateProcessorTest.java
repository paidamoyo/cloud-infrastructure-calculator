package com.calculator.application.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.calculator.domain.CloudInstance;
import com.calculator.domain.Host;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class HostStateProcessorTest {

    private Path currentDirectory;

    @Before
    public void setUp() throws Exception {
        currentDirectory = Paths.get("").toAbsolutePath();

    }

    @Test
    public void shouldGenerateHostsFromFileAndCloudInstances() throws Exception {

        //given
        Path pathHostState = Paths.get(String.format("%s/src/test/resources/HostState.txt", currentDirectory.toString()));

        CloudInstance instanceOne = CloudInstance.from("1", "8", "2");
        CloudInstance instanceTwo = CloudInstance.from("2", "8", "2");
        CloudInstance instanceThree = CloudInstance.from("3", "8", "2");
        CloudInstance instanceFour = CloudInstance.from("4", "8", "7");
        CloudInstance instanceFive = CloudInstance.from("5", "15", "7");
        CloudInstance instanceSix = CloudInstance.from("6", "16", "9");
        CloudInstance instanceSeven = CloudInstance.from("7", "13", "9");
        CloudInstance instanceEight = CloudInstance.from("8", "9", "3");
        CloudInstance instanceNine = CloudInstance.from("9", "13", "3");
        CloudInstance instanceTen = CloudInstance.from("10", "16", "5");
        CloudInstance instanceEleven = CloudInstance.from("11", "15", "8");
        CloudInstance instanceTwelve = CloudInstance.from("12", "8", "6");
        CloudInstance instanceThirteen = CloudInstance.from("13", "16", "8");
        CloudInstance instanceFourteen = CloudInstance.from("14", "9", "9");
        CloudInstance instanceFifteen = CloudInstance.from("15", "9", "7");

        List<CloudInstance> cloudInstances = Arrays.asList(instanceOne, instanceTwo, instanceThree, instanceFour, instanceFive, instanceSix, instanceSeven, instanceEight, instanceNine, instanceTen, instanceEleven, instanceTwelve, instanceThirteen, instanceFourteen, instanceFifteen);

        HostStateProcessor hostStateProcessor = new HostStateProcessor(pathHostState, cloudInstances);

        //when
        List<Host> hosts = hostStateProcessor.process();

        //then
        Host hostTwo = Host.from("2", 4, "0", Arrays.asList(instanceOne, instanceTwo, instanceThree));
        Host hostFive = Host.from("5", 4, "0", Collections.singletonList(instanceTen));
        Host hostSeven = Host.from("7", 3, "0", Arrays.asList(instanceFour, instanceFive, instanceFifteen));
        Host hostNine = Host.from("9", 3, "1", Arrays.asList(instanceSix, instanceSeven, instanceFourteen));
        Host hostThree = Host.from("3", 3, "1", Arrays.asList(instanceEight, instanceNine));
        Host hostTen = Host.from("10", 2, "2", Collections.emptyList());
        Host hostSix = Host.from("6", 4, "2", Collections.singletonList(instanceTwelve));
        Host hostEight = Host.from("8", 2, "2", Arrays.asList(instanceEleven, instanceThirteen));


        assertThat(hosts, is(Arrays.asList(hostTwo, hostFive, hostSeven, hostNine, hostThree, hostTen, hostSix,
                hostEight)));
    }


    @Test(expected = HostStateProcessorException.class)
    public void shouldThrowExceptionIfHostStateFileIsInvalid() throws Exception {

        //given
        Path pathHostState = Paths.get(String.format("%s/src/test/resources/InvalidHostState.txt", currentDirectory.toString()));

        List<CloudInstance> cloudInstances = Collections.emptyList();

        HostStateProcessor hostStateProcessor = new HostStateProcessor(pathHostState, cloudInstances);

        //when
        hostStateProcessor.process();
    }

    @Test(expected = HostStateProcessorException.class)
    public void shouldThrowExceptionIfHostStateFileIsNotFound() throws Exception {

        //given
        Path pathHostState = Paths.get(String.format("%s/src/test/resources/HostStateNotFound.txt", currentDirectory.toString()));

        List<CloudInstance> cloudInstances = Collections.emptyList();

        HostStateProcessor hostStateProcessor = new HostStateProcessor(pathHostState, cloudInstances);

        //when
        hostStateProcessor.process();
    }
}