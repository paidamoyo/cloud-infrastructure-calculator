package com.calculator.application.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.calculator.domain.CloudInstance;
import com.google.common.collect.FluentIterable;

public class InstanceStateProcessor implements FileProcessorBase {

    private static final String INSTANCE_STATE_LINE_SEPARATOR = ",";
    private static final int INSTANCE_LINE_LENGTH = 3;
    private Path path;

    public InstanceStateProcessor(Path path) {

        this.path = path;
    }

    @Override
    public List<CloudInstance> process() {
        return getCloudInstances();
    }

    private List<CloudInstance> getCloudInstances() {
        try {
            return FluentIterable.from(Files.readAllLines(this.path))
                    .transform(this::createCloudInstance)
                    .toList();
        } catch (IOException e) {
            String message = "error reading file: " + this.path + " ";
            System.out.println(message + e);
            throw new InstanceStateProcessorException(message, e);

        }
    }

    private CloudInstance createCloudInstance(String line) {

        String[] cloudInstance = line.split(INSTANCE_STATE_LINE_SEPARATOR);


        if (cloudInstance.length != INSTANCE_LINE_LENGTH
                ) {
            String message = String.format("error in file: %s line: %s is not formatted correctly", path, line);
            throw new InstanceStateProcessorException(message);
        }

        String id = cloudInstance[0];
        String customerId = cloudInstance[1];
        String hostId = cloudInstance[2];

        return CloudInstance.from(id, customerId, hostId);
    }
}
