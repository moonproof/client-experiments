package com.moonproof.controller;

import com.moonproof.dto.ExperimentDto;
import com.moonproof.dto.StatisticDto;
import com.moonproof.service.ExperimentService;
import com.moonproof.service.StatisticService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/experiment")
public class ExperimentController {

    private final List<ExperimentService> experimentPriceService;
    private final StatisticService statisticService;

    public ExperimentController(List<ExperimentService> experimentPriceService, StatisticService statisticService) {
        this.experimentPriceService = experimentPriceService;
        this.statisticService = statisticService;
    }

    @GetMapping
    public ResponseEntity<List<ExperimentDto>> get(@RequestHeader("Device-Token") String token) {
        if (token == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(experimentPriceService.stream()
                .map(experimentService -> experimentService.get(token))
                .toList());
    }

    @GetMapping("/statistic")
    public ResponseEntity<StatisticDto> getStatistic() {
        return ResponseEntity.ok(statisticService.generateStats());
    }

}
