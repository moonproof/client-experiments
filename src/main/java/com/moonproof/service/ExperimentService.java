package com.moonproof.service;

import com.moonproof.dto.ExperimentDto;

public interface ExperimentService {
    ExperimentDto get(String deviceToken);

    String getExperimentName();
}
