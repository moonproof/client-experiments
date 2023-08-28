package com.moonproof.controller;

import com.moonproof.dto.ExperimentDto;
import com.moonproof.dto.StatisticDto;
import com.moonproof.service.DeviceService;
import com.moonproof.service.ExperimentService;
import com.moonproof.service.StatisticService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(path = "/api/experiment")
public class ExperimentController {

    private final List<ExperimentService> experimentPriceService;
    private final StatisticService statisticService;
    private final DeviceService deviceService;

    public ExperimentController(List<ExperimentService> experimentPriceService, StatisticService statisticService, DeviceService deviceService) {
        this.experimentPriceService = experimentPriceService;
        this.statisticService = statisticService;
        this.deviceService = deviceService;
    }

    @GetMapping
    public ResponseEntity<List<ExperimentDto>> get(@RequestHeader("Device-Token") String token) {
        if (token == null) {
            return ResponseEntity.badRequest().build();
        }
        deviceService.add(token);
        return ResponseEntity.ok(experimentPriceService.stream()
                .map(experimentService -> experimentService.get(token))
                .filter(Objects::nonNull)
                .toList());
    }

    @GetMapping("/statistic")
    public ResponseEntity<StatisticDto> getStatistic() {
        return ResponseEntity.ok(statisticService.generateStats());
    }

}
