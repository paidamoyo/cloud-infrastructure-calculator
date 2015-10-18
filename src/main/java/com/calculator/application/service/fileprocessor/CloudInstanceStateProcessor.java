package com.calculator.application.service.fileprocessor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.calculator.domain.CloudInstance;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;

public class CloudInstanceStateProcessor implements FileProcessorBase {

    private static final String INSTANCE_STATE_LINE_SEPARATOR = ",";
    private static final int INSTANCE_LINE_LENGTH = 3;
    private Path path;

    public CloudInstanceStateProcessor(Path path) {

        this.path = path;
    }

    @Override
    public ImmutableList<CloudInstance> process() {
        return getCloudInstances();
    }

    private ImmutableList<CloudInstance> getCloudInstances() {
        try {
            return FluentIterable.from(Files.readAllLines(this.path))
                    .transform(this::createCloudInstance)
                    .toList();
        } catch (IOException e) {
            String message = "error reading file: " + this.path + " ";
            System.out.println(message + e);
            throw new CloudInstanceStateProcessorException(message, e);

        }
    }

    private CloudInstance createCloudInstance(String line) {

        String[] cloudInstance = line.split(INSTANCE_STATE_LINE_SEPARATOR);


        if (cloudInstance.length != INSTANCE_LINE_LENGTH
                ) {
            String message = String.format("error in file: %s line: %s is not formatted correctly", path, line);
            throw new CloudInstanceStateProcessorException(message);
        }

        String id = cloudInstance[0];
        String customerId = cloudInstance[1];
        String hostId = cloudInstance[2];

        return CloudInstance.from(id, customerId, hostId);
    }
}
