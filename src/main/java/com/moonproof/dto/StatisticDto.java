package com.moonproof.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatisticDto {
    private long countDevices;
    private Map<String, Map<String, Integer>> stats;
}
