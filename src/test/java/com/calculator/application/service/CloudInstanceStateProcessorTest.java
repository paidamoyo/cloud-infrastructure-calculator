package com.calculator.application.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.calculator.domain.CloudInstance;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CloudInstanceStateProcessorTest {

    private Path currentDirectory;

    @Before
    public void setUp() throws Exception {
        currentDirectory = Paths.get("").toAbsolutePath();

    }

    @Test
    public void shouldCreateCloudInstancesFromFile() throws Exception {

        //given
        Path pathInstateState = Paths.get(String.format("%s/src/test/resources/InstanceState.txt", currentDirectory.toString()));

        CloudInstanceStateProcessor processor = new CloudInstanceStateProcessor(pathInstateState);

        //when
        List<CloudInstance> cloudInstances = processor.process();

        //then
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
        assertThat(cloudInstances, is(Arrays.asList(instanceOne, instanceTwo, instanceThree, instanceFour, instanceFive, instanceSix, instanceSeven, instanceEight, instanceNine, instanceTen, instanceEleven, instanceTwelve, instanceThirteen, instanceFourteen, instanceFifteen)));
    }


    @Test(expected = CloudInstanceStateProcessorException.class)
    public void shouldThrowExceptionIfFIleIsNotFormattedCorrectly() throws Exception {

        //given
        Path pathInstateState = Paths.get(String.format("%s/src/test/resources/InvalidInstanceState.txt", currentDirectory.toString()));

        CloudInstanceStateProcessor processor = new CloudInstanceStateProcessor(pathInstateState);


        //when
        processor.process();

        //then
    }

    @Test(expected = CloudInstanceStateProcessorException.class)
    public void shouldThrowExceptionIfFIleIsNotFound() throws Exception {

        //given
        Path pathInstateState = Paths.get(String.format("%s/src/test/resources/NotExistingInstanceState.txt", currentDirectory.toString()));

        CloudInstanceStateProcessor processor = new CloudInstanceStateProcessor(pathInstateState);


        //when
        processor.process();

        //then
    }
}