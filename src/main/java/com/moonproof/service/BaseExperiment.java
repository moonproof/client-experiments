package com.moonproof.service;

import com.moonproof.domain.Experiment;
import com.moonproof.dto.ExperimentDto;

public abstract class BaseExperiment implements ExperimentService {

    protected ExperimentDto newExperimentDto(Experiment experiment) {
        return new ExperimentDto(getKey(), experiment.getValue());
    }

    protected ExperimentDto newExperimentDto(String value) {
        return new ExperimentDto(getKey(), value);
    }
}
