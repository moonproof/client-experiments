package com.moonproof.service;

import com.moonproof.domain.Experiment;
import com.moonproof.dto.StatisticDto;
import com.moonproof.store.ExperimentStore;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticServiceImpl implements StatisticService {

    private final ExperimentStore experimentStore;

    private final List<ExperimentService> experimentServices;

    public StatisticServiceImpl(ExperimentStore experimentStore, List<ExperimentService> experimentServices) {
        this.experimentStore = experimentStore;
        this.experimentServices = experimentServices;
    }

    @Override
    public StatisticDto generateStats() {
        Map<String, Map<String, Integer>> stats = new HashMap<>();
        experimentServices.forEach((service) -> stats.put(service.getExperimentName(), new HashMap<>()));

        StatisticDto statistic = new StatisticDto();
        List<Experiment> experiments = experimentStore.getAll();

        experiments.forEach(e -> {
            Map<String, Integer> innerStats = stats.get(e.getName());
            innerStats.merge(e.getValue(), 1, Integer::sum);
        });

        statistic.setStats(stats);
        statistic.setCountDevices(experiments.stream()
                .map(Experiment::getToken)
                .distinct()
                .count());
        return statistic;
    }
}
