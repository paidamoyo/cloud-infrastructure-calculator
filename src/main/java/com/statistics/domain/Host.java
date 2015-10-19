package com.statistics.domain;

import java.util.List;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Host {

    private final String id;
    private final int numberOfSlots;
    private final String dataCenterId;
    private final List<CloudInstance> cloudInstances;

    public static Host from(String id, int numberOfSlots, String dataCentreId, List<CloudInstance> cloudInstances) {
        return new Host(id, numberOfSlots, dataCentreId, cloudInstances);
    }

    public boolean hasEmptySlot() {
        return this.cloudInstances.size() < this.numberOfSlots;
    }
}
