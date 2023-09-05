package com.moonproof.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class DeviceDomain {
    private String token;
    private LocalDateTime firstRequest;
}
