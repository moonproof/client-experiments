package com.moonproof.service;

import com.moonproof.domain.Experiment;
import com.moonproof.dto.ExperimentDto;
import com.moonproof.exception.ExperimentLogicException;
import com.moonproof.store.ExperimentStore;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Map;

@Service
public class ExperimentPriceService extends BaseExperiment {

    private final static String EXPERIMENT_KEY = "price";

    // KEY - price. VALUE - reminder from 20
    private final static Map<Integer, Integer> OPTIONS = Map.of(
            10, 14,
            20, 16,
            50, 17,
            5, 19
    );

    private BigDecimal counter = new BigDecimal(1);

    private final ExperimentStore experimentPriceStore;

    public ExperimentPriceService(ExperimentStore experimentStore) {
        this.experimentPriceStore = experimentStore;
    }

    @Override
    public ExperimentDto get(String deviceToken) {
        return experimentPriceStore.get(getKey(), deviceToken)
                .map(this::newExperimentDto)
                .orElseGet(() -> {
                    String currentOption = getCurrentOption();
                    experimentPriceStore.put(new Experiment(deviceToken, getKey(), currentOption));
                    return new ExperimentDto(EXPERIMENT_KEY, currentOption);
                });
    }

    private String getCurrentOption() {
        int reminder = counter.remainder(new BigDecimal(20)).intValue();
        counter = counter.add(new BigDecimal(1));
        return OPTIONS.keySet().stream()
                .filter(k -> reminder <= OPTIONS.get(k))
                .min(Comparator.comparing(OPTIONS::get))
                .orElseThrow(() -> new ExperimentLogicException("Dont found any option for current device"))
                .toString();

    }

    @Override
    public String getKey() {
        return EXPERIMENT_KEY;
    }
}
