package com.moonproof.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class DeviceDomain {
    private String device;
    private LocalDateTime firstRequest;
}
