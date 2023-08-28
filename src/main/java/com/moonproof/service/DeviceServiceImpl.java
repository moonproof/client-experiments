package com.moonproof.service;

import com.moonproof.domain.DeviceDomain;
import com.moonproof.store.DeviceStorage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceStorage deviceStorage;

    public DeviceServiceImpl(DeviceStorage deviceStorage) {
        this.deviceStorage = deviceStorage;
    }

    @Override
    public void add(String device) {
        deviceStorage.add(new DeviceDomain(device, LocalDateTime.now()));
    }

    @Override
    public Optional<DeviceDomain> get(String device) {
        return deviceStorage.get(device);
    }
}
