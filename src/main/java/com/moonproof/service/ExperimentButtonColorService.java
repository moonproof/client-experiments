package com.moonproof.service;

import com.moonproof.domain.Experiment;
import com.moonproof.dto.ExperimentDto;
import com.moonproof.store.ExperimentStoreImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ExperimentButtonColorService extends BaseExperiment {

    private final static String EXPERIMENT_KEY = "button_color";
    private final static List<String> OPTIONS = List.of("#FF0000", "#00FF00", "#0000FF");
    private BigDecimal counter = new BigDecimal(1);

    private final ExperimentStoreImpl experimentStoreImpl;

    public ExperimentButtonColorService(ExperimentStoreImpl experimentStoreImpl) {
        this.experimentStoreImpl = experimentStoreImpl;
    }

    @Override
    public ExperimentDto get(String deviceToken) {
        return experimentStoreImpl.get(getKey(), deviceToken)
                .map(this::newExperimentDto)
                .orElseGet(() -> {
                    String currentOption = getCurrentOption();
                    experimentStoreImpl.put(new Experiment(deviceToken, getKey(), currentOption));
                    return newExperimentDto(currentOption);
                });
    }

    @Override
    public String getKey() {
        return EXPERIMENT_KEY;
    }

    private String getCurrentOption() {
        int index = counter.remainder(new BigDecimal(OPTIONS.size())).intValue();
        counter = counter.add(new BigDecimal(1));
        return OPTIONS.get(index);
    }
}
