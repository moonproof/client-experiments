package com.moonproof.service;

import com.moonproof.domain.Experiment;
import com.moonproof.dto.ExperimentDto;
import com.moonproof.store.ExperimentStore;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExperimentButtonColorService extends BaseExperiment {

    private final static String EXPERIMENT_KEY = "button_color";
    private final static List<String> OPTIONS = List.of("#FF0000", "#00FF00", "#0000FF");
    private BigDecimal counter = new BigDecimal(1);

    private final ExperimentStore experimentStoreImpl;
    private final LocalDateTime createdDate;

    public ExperimentButtonColorService(ExperimentStore experimentStoreImpl) {
        this.experimentStoreImpl = experimentStoreImpl;
        createdDate = LocalDateTime.now().plusMinutes(3);
    }

    public ExperimentDto getByDeviceToken(String deviceToken) {
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

    @Override
    protected LocalDateTime getCreatedDate() {
        return createdDate;
    }

    private String getCurrentOption() {
        int index = counter.remainder(new BigDecimal(OPTIONS.size())).intValue();
        counter = counter.add(new BigDecimal(1));
        return OPTIONS.get(index);
    }
}
