package com.moonproof.store;

import com.moonproof.domain.DeviceDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DeviceStorageImplTest {

    private DeviceStorage deviceStorage;

    @Test
    void get() {
        DeviceDomain expectedDevice = new DeviceDomain("some", LocalDateTime.now());

        Object devices = ReflectionTestUtils.getField(deviceStorage, "devices");
        assertNotNull(devices, "Devices should be exist");

        ReflectionTestUtils.setField(deviceStorage, "devices", List.of(expectedDevice));

        Optional<DeviceDomain> actualDevice = deviceStorage.get(expectedDevice.getToken());
        assertTrue(actualDevice.isPresent(), "Device should be exist");
        assertEquals(actualDevice.get(), expectedDevice, "Device should be expected");
    }

    @Test
    void add() {
        DeviceDomain deviceDomain = new DeviceDomain("some", LocalDateTime.now());
        List<DeviceDomain> expectedDevices = new ArrayList<>();
        expectedDevices.add(deviceDomain);
        deviceStorage.add(deviceDomain);

        Object actualDevices = ReflectionTestUtils.getField(deviceStorage, "devices");
        assertNotNull(actualDevices, "Devices should be exist");
        assertEquals(expectedDevices, actualDevices, "Devices should be expected");
    }

    @BeforeEach
    void setUp() {
        deviceStorage = new DeviceStorageImpl();
    }
}