package com.moonproof.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Experiment {
    private String token;
    private String name;
    private String value;
}
