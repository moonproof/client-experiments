package com.moonproof.service;

import com.moonproof.domain.DeviceDomain;

import java.util.Optional;

public interface DeviceService {
    void add(String device);
    Optional<DeviceDomain> get(String device);
}
