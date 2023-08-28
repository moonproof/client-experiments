package com.moonproof.store;

import com.moonproof.domain.DeviceDomain;

import java.util.Optional;

public interface DeviceStorage {
    Optional<DeviceDomain> get(String device);
    void add(DeviceDomain device);
}
