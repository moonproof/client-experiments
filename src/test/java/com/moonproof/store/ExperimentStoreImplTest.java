package com.moonproof.store;

import com.moonproof.domain.Experiment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExperimentStoreImplTest {

    private ExperimentStore experimentStore;

    @BeforeEach
    void setUp() {
        experimentStore = new ExperimentStoreImpl();
    }

    @Test
    void get() {
        List<Experiment> expectedExperiments = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Experiment experiment = new Experiment(UUID.randomUUID().toString(),
                    UUID.randomUUID().toString(),
                    UUID.randomUUID().toString());
            expectedExperiments.add(experiment);
        }

        ReflectionTestUtils.setField(experimentStore, "store", expectedExperiments);

        for (Experiment expectedExperiment : expectedExperiments) {
            Optional<Experiment> actualExperiment =
                    experimentStore.get(expectedExperiment.getName(), expectedExperiment.getToken());
            assertTrue(actualExperiment.isPresent(), "Experiment should be returned");
            assertEquals(expectedExperiment,
                    actualExperiment.get(),
                    "Experiment should be expected");
        }
    }

    @Test
    void getNotFound() {
        Experiment experiment = new Experiment(UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString());

        assertTrue(experimentStore.get(experiment.getName(), experiment.getToken()).isEmpty(),
                "Experiment should be not found");
    }

    @Test
    void getAll() {
        List<Experiment> expectedExperiments = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Experiment experiment = new Experiment(UUID.randomUUID().toString(),
                    UUID.randomUUID().toString(),
                    UUID.randomUUID().toString());
            expectedExperiments.add(experiment);
        }
        ReflectionTestUtils.setField(experimentStore, "store", expectedExperiments);
        assertEquals(expectedExperiments, experimentStore.getAll());
    }

    @Test
    void put() {
        List<Experiment> expectedExperiments = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Experiment experiment = new Experiment(UUID.randomUUID().toString(),
                    UUID.randomUUID().toString(),
                    UUID.randomUUID().toString());
            expectedExperiments.add(experiment);
            experimentStore.put(experiment);
        }

        Object actualExperiments = ReflectionTestUtils.getField(experimentStore, "store");
        assertEquals(expectedExperiments, actualExperiments, "Experiments should be expected");
    }
}