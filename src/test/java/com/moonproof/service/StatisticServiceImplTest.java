package com.moonproof.service;

import com.moonproof.domain.Experiment;
import com.moonproof.store.ExperimentStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatisticServiceImplTest {

    private StatisticService statisticService;

    @Mock
    private ExperimentService priceService;
    @Mock
    private ExperimentService colorService;
    @Mock
    private ExperimentStore experimentStore;

    @BeforeEach
    void setUp() {
        statisticService = new StatisticServiceImpl(experimentStore, List.of(priceService, colorService));
    }

    @Test
    void generateStatsCheckStats() {
        Map<String, Map<String, Integer>> expectedStats = new HashMap<>();
        List<Experiment> experiments = new ArrayList<>();

        // color
        Map<String, Integer> statsColor = new HashMap<>();
        int colorExperimentsCount = 700;
        statsColor.put("0", colorExperimentsCount / 2);
        statsColor.put("1", colorExperimentsCount / 2);
        String colorExperimentName = "color";
        expectedStats.put(colorExperimentName, statsColor);
        for (int i = 0; i < colorExperimentsCount; i++) {
            Experiment experiment = new Experiment(UUID.randomUUID().toString(),
                    colorExperimentName,
                    String.valueOf(i % 2));
            experiments.add(experiment);
        }

        // price
        Map<String, Integer> statsPrice = new HashMap<>();
        int priceExperimentsCount = 300;
        statsPrice.put("0", priceExperimentsCount / 2);
        statsPrice.put("1", priceExperimentsCount / 2);
        String priceExperimentName = "price";
        expectedStats.put(priceExperimentName, statsPrice);

        for (int i = 0; i < priceExperimentsCount; i++) {
            Experiment experiment = new Experiment(UUID.randomUUID().toString(),
                    priceExperimentName,
                    String.valueOf(i % 2));
            experiments.add(experiment);
        }

        when(experimentStore.getAll()).thenReturn(experiments);
        when(colorService.getExperimentName()).thenReturn(colorExperimentName);
        when(priceService.getExperimentName()).thenReturn(priceExperimentName);

        assertEquals(expectedStats, statisticService.generateStats().getStats());
    }

    @Test
    void generateStatsCheckDeviceCount() {
        List<Experiment> experiments = new ArrayList<>();

        String priceExperimentName = "2";
        for (int i = 0; i < 1000; i++) {
            Experiment experiment = new Experiment(UUID.randomUUID().toString(),
                    priceExperimentName,
                    UUID.randomUUID().toString());
            experiments.add(experiment);
        }
        String colorExperimentName = "1";
        for (int i = 1000; i > 0; i -= 2) {
            Experiment experiment = new Experiment(String.valueOf(i),
                    colorExperimentName,
                    UUID.randomUUID().toString());
            experiments.add(experiment);
        }

        long expectedCountDevices = experiments.stream()
                .map(Experiment::getToken)
                .distinct()
                .count();

        when(experimentStore.getAll()).thenReturn(experiments);
        when(colorService.getExperimentName()).thenReturn(colorExperimentName);
        when(priceService.getExperimentName()).thenReturn(priceExperimentName);

        assertEquals(expectedCountDevices, statisticService.generateStats().getCountDevices());
    }
}