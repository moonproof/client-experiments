package com.moonproof.service;

import com.moonproof.domain.DeviceDomain;
import com.moonproof.dto.ExperimentDto;
import com.moonproof.exception.ExperimentLogicException;
import com.moonproof.store.ExperimentStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExperimentPriceServiceTest {

    private ExperimentPriceService experimentPriceService;
    @Mock
    private ExperimentStore experimentStore;
    @Mock
    private DeviceService deviceService;

    @BeforeEach
    void setUp() {
        experimentPriceService = new ExperimentPriceService(experimentStore);
    }

    @ParameterizedTest
    @MethodSource("devices")
    void getByDeviceToken(List<String> devices) {
        final Map<String, Integer> expectedResult = Map.of(
                "10", 15,
                "20", 2,
                "50", 1,
                "5", 2
        );
        List<ExperimentDto> experiments = new ArrayList<>();
        for (String device : devices) {
            experiments.add(experimentPriceService.getByDeviceToken(device));
        }

        Map<String, Integer> result = new HashMap<>();
        for (ExperimentDto experiment : experiments) {
            Integer value = result.get(experiment.getValue());
            if (value != null) {
                result.put(experiment.getValue(), ++value);
            } else {
                result.put(experiment.getValue(), 1);
            }
        }

        assertEquals(expectedResult, result, "Statistic by price should be expected");
    }

    private static Stream<List<String>> devices() {
        List<String> deviceName = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            deviceName.add(String.valueOf(i));
        }

        return Stream.of(deviceName);
    }

    @Test
    void getCreatedDate() {
        Object createdDate = ReflectionTestUtils.getField(experimentPriceService, "createdDate");
        assertEquals(createdDate, experimentPriceService.getCreatedDate());
    }

    @Test
    void getKey() {
        assertEquals("price", experimentPriceService.getExperimentName());
    }

    @Test
    void getPositive() {
        String deviceName = "111";
        LocalDateTime now = LocalDateTime.now();

        ReflectionTestUtils.setField(experimentPriceService, "deviceService", deviceService);
        ReflectionTestUtils.setField(experimentPriceService, "createdDate", now);

        when(deviceService.get(deviceName))
                .thenReturn(Optional.of(new DeviceDomain(deviceName, now.plusMinutes(1))));

        ExperimentDto actualExperiment = experimentPriceService.get(deviceName);
        assertNotNull(actualExperiment, "Experiment should be not null");
        assertEquals(experimentPriceService.getExperimentName(), actualExperiment.getName(),
                "Experiment name should be expected");
    }

    @Test
    void getNegative() {
        String deviceName = "111";
        LocalDateTime now = LocalDateTime.now();

        ReflectionTestUtils.setField(experimentPriceService, "deviceService", deviceService);
        ReflectionTestUtils.setField(experimentPriceService, "createdDate", now);

        when(deviceService.get(deviceName))
                .thenReturn(Optional.of(new DeviceDomain(deviceName, now.minusMinutes(1))));

        ExperimentDto actualExperiment = experimentPriceService.get(deviceName);
        assertNull(actualExperiment, "Experiment should be null");
    }

    @Test()
    void getException() {
        String deviceName = "111";
        LocalDateTime now = LocalDateTime.now();

        ReflectionTestUtils.setField(experimentPriceService, "deviceService", deviceService);
        ReflectionTestUtils.setField(experimentPriceService, "createdDate", now);

        when(deviceService.get(deviceName))
                .thenReturn(Optional.empty());
        Exception exception = assertThrows(ExperimentLogicException.class,
                () -> experimentPriceService.get(deviceName));
        assertEquals("Device not found in repository", exception.getMessage(),
                "Message in exception should be expected");
    }
}