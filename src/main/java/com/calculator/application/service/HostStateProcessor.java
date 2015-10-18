package com.calculator.application.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.calculator.domain.Host;
import com.calculator.domain.CloudInstance;
import com.google.common.collect.FluentIterable;

public class HostStateProcessor implements FileProcessorBase {

    private static final String HOST_STATE_LINE_SEPARATOR = ",";
    private static final int HOST_LINE_LENGTH = 3;

    private Path path;
    private InstanceStateProcessor instanceStateProcessor;

    public HostStateProcessor(Path path, InstanceStateProcessor instanceStateProcessor) {
        this.path = path;
        this.instanceStateProcessor = instanceStateProcessor;
    }

    @Override
    public List<Host> process() {
        return getCloudHosts(this.instanceStateProcessor.process());
    }

    private List<Host> getCloudHosts(List<CloudInstance> cloudInstances) {
        try {
            return FluentIterable.from(Files.readAllLines(this.path))
                    .transform(line -> createCloudHost(line, cloudInstances))
                    .toList();
        } catch (IOException e) {
            String message = "error reading file: " + this.path + " ";
            System.out.println(message + e);
            throw new HostStateProcessorException(message, e);

        }
    }

    private Host createCloudHost(String line, List<CloudInstance> cloudInstances) {

        String[] cloudHost = line.split(HOST_STATE_LINE_SEPARATOR);


        if (cloudHost.length != HOST_LINE_LENGTH
                ) {
            String message = String.format("error in file: %s line: %s is not formatted correctly", path, line);
            throw new HostStateProcessorException(message);
        }

        String id = cloudHost[0];
        String numberOfSlots = cloudHost[1];
        String dataCentreId = cloudHost[2];

        List<CloudInstance> cloudInstancesByHostId = findCloudInstancesByHostId(id, cloudInstances);

        return Host.from(id, Integer.valueOf(numberOfSlots), dataCentreId, cloudInstancesByHostId);
    }

    private List<CloudInstance> findCloudInstancesByHostId(String hostId, List<CloudInstance> cloudInstances) {
        return FluentIterable.from(cloudInstances).filter(cloudInstance -> {
            return cloudInstance.getHostId().equals(hostId);
        }).toList();
    }
}