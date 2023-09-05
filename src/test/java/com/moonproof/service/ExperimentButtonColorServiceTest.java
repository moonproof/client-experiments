package com.moonproof.service;

import com.moonproof.domain.Experiment;
import com.moonproof.dto.ExperimentDto;
import com.moonproof.store.ExperimentStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExperimentButtonColorServiceTest {

    private BaseExperiment experimentButtonColorService;
    @Mock
    private ExperimentStore experimentStore;

    @BeforeEach
    void setUp() {
        experimentButtonColorService = new ExperimentButtonColorService(experimentStore);
    }

    @Test
    void getByDeviceTokenSecondAdding() {
        String deviceToken = UUID.randomUUID().toString();
        ExperimentDto expectedExperiment = new ExperimentDto(experimentButtonColorService.getExperimentName(), "some name");
        when(experimentStore.get(any(), any()))
                .thenReturn(Optional.of(
                        new Experiment(deviceToken, expectedExperiment.getName(), expectedExperiment.getValue()))
                );

        ExperimentDto experimentDto = experimentButtonColorService.getByDeviceToken(deviceToken);

        assertEquals(expectedExperiment, experimentDto, "Experiment should be expected");
    }

    @Test
    void getByDeviceTokenFirstAdding() {
        Map<String, Integer> expectedStats = Map.of(
                "#FF0000", 100,
                "#00FF00", 100,
                "#0000FF", 100);
        List<String> deviceNames = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            deviceNames.add(String.valueOf(i));
        }
        when(experimentStore.get(any(), any())).thenReturn(Optional.empty());

        List<ExperimentDto> experiments = new ArrayList<>();
        for (String deviceToken : deviceNames) {
            experiments.add(experimentButtonColorService.getByDeviceToken(deviceToken));
        }

        Map<String, Integer> actualStats = new HashMap<>();
        for (ExperimentDto experiment : experiments) {
            Integer currentValue = actualStats.get(experiment.getValue());
            if (currentValue == null) {
                actualStats.put(experiment.getValue(), 1);
            } else {
                actualStats.put(experiment.getValue(), ++ currentValue);
            }
        }

        assertEquals(expectedStats, actualStats, "Stats by color button should be expected");
    }

    @Test
    void getKey() {
        assertEquals(experimentButtonColorService.getExperimentName(), "button_color");
    }
}