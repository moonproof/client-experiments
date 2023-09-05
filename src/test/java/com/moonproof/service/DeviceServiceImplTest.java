package com.moonproof.service;

import com.moonproof.domain.DeviceDomain;
import com.moonproof.store.DeviceStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeviceServiceImplTest {

    private DeviceService deviceService;
    @Mock
    private DeviceStorage deviceStorage;

    @Test
    void addCheckStorageUsing() {
        String deviceName = "some device name";
        deviceService.add(deviceName);

        verify(deviceStorage).add(new DeviceDomain(deviceName, Mockito.any()));
    }

    @Test
    void get() {
        DeviceDomain expectedDevice = new DeviceDomain("some device name", LocalDateTime.now());
        when(deviceStorage.get(expectedDevice.getToken()))
                .thenReturn(Optional.of(expectedDevice));

        Optional<DeviceDomain> actualDeviceDomain = deviceService.get(expectedDevice.getToken());
        assertTrue(actualDeviceDomain.isPresent(), "Device should be exist");

        DeviceDomain actualDevice = actualDeviceDomain.get();
        assertEquals(actualDevice.getToken(), expectedDevice.getToken(),
                "Device name should be expected");
        assertEquals(actualDevice.getFirstRequest(), expectedDevice.getFirstRequest(),
                "Device first date request should be expected");
    }

    @BeforeEach
    void setUp() {
        deviceService = new DeviceServiceImpl(deviceStorage);
    }
}