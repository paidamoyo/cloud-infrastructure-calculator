package com.statistics.domain;

import java.util.Arrays;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HostTest {

    @Test
    public void shouldHasEmptySlotBeTrueWhenNumberOfSlotsIsGreaterThatClusters() throws Exception {

        //given
        CloudInstance instanceOne = CloudInstance.from("1", "8", "2");
        CloudInstance instanceTwo = CloudInstance.from("2", "8", "2");
        CloudInstance instanceThree = CloudInstance.from("3", "8", "2");

        //when
        Host host = Host.from("2", 4, "0", Arrays.asList(instanceOne, instanceTwo, instanceThree));

        //then
        assertTrue(host.hasEmptySlot());
    }

    @Test
    public void shouldHasEmptySlotBeFalseWhenNumberOfSlotsIsEqualToClusters() throws Exception {

        //given
        CloudInstance instanceOne = CloudInstance.from("1", "8", "2");
        CloudInstance instanceTwo = CloudInstance.from("2", "8", "2");
        CloudInstance instanceThree = CloudInstance.from("3", "8", "2");

        //when
        Host host = Host.from("2", 3, "0", Arrays.asList(instanceOne, instanceTwo, instanceThree));

        //then
        assertFalse(host.hasEmptySlot());
    }

}