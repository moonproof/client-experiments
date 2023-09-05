package com.moonproof.controller;

import com.moonproof.dto.ExperimentDto;
import com.moonproof.dto.StatisticDto;
import com.moonproof.service.DeviceService;
import com.moonproof.service.ExperimentService;
import com.moonproof.service.StatisticService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExperimentControllerTest {

    private ExperimentController experimentController;
    @Mock
    private ExperimentService colorService;
    @Mock
    private ExperimentService priceService;
    @Mock
    private StatisticService statisticService;
    @Mock
    private DeviceService deviceService;

    @BeforeEach
    void setUp() {
        experimentController = new ExperimentController(List.of(colorService, priceService), statisticService, deviceService);
    }

    @Test
    void getNull() {
        ResponseEntity<Object> expectedRs = ResponseEntity.badRequest().build();

        ResponseEntity<List<ExperimentDto>> actualRs = experimentController.get(null);
        assertEquals(expectedRs, actualRs);
    }

    @Test
    void getDeviceAdding() {
        experimentController.get(UUID.randomUUID().toString());
        verify(deviceService, times(1)).add(any());
    }

    @Test
    void getReal() {
        ExperimentDto colorExperiment = new ExperimentDto("1", "2");
        ExperimentDto priceExperiment = new ExperimentDto("3", "4");
        ResponseEntity<List<ExperimentDto>> expectedRs = ResponseEntity.ok(List.of(colorExperiment, priceExperiment));

        when(colorService.get(any())).thenReturn(colorExperiment);
        when(priceService.get(any())).thenReturn(priceExperiment);

        ResponseEntity<List<ExperimentDto>> actualRs = experimentController.get(UUID.randomUUID().toString());

        assertEquals(expectedRs, actualRs);
    }

    @Test
    void getStatistic() {
        StatisticDto statisticDto = new StatisticDto(10, new HashMap<>());
        ResponseEntity<StatisticDto> expectedRs = ResponseEntity.ok(statisticDto);

        when(statisticService.generateStats()).thenReturn(statisticDto);
        ResponseEntity<StatisticDto> actualRs = experimentController.getStatistic();
        assertEquals(expectedRs, actualRs);
    }
}