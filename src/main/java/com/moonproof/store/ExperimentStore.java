package com.moonproof.store;

import com.moonproof.domain.Experiment;

import java.util.List;
import java.util.Optional;

public interface ExperimentStore {
    Optional<Experiment> get(String key, String token);

    List<Experiment> getAll();

    void put(Experiment experiment);
}
