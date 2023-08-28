package com.moonproof.store;

import com.moonproof.domain.DeviceDomain;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class DeviceStorageImpl implements DeviceStorage {
    private final List<DeviceDomain> devices = new ArrayList<>();
    @Override
    public Optional<DeviceDomain> get(String device) {
        return devices.stream()
                .filter(d -> device.equals(d.getDevice()))
                .findFirst();
    }

    @Override
    public void add(DeviceDomain device) {
        devices.add(device);
    }
}
