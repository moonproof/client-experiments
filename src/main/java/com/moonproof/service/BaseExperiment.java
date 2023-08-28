package com.moonproof.service;

import com.moonproof.domain.DeviceDomain;
import com.moonproof.domain.Experiment;
import com.moonproof.dto.ExperimentDto;
import com.moonproof.exception.ExperimentLogicException;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public abstract class BaseExperiment implements ExperimentService {

    @Autowired
    private DeviceService deviceService;

    @Override
    public ExperimentDto get(String deviceToken) {
        DeviceDomain device = deviceService.get(deviceToken)
                .orElseThrow(() -> new ExperimentLogicException("Device not found in repository"));
        if (device.getFirstRequest().isAfter(getCreatedDate())) {
            return this.getByDeviceToken(deviceToken);
        } else {
            return null;
        }
    }

    abstract protected ExperimentDto getByDeviceToken(String deviceToken);

    protected ExperimentDto newExperimentDto(Experiment experiment) {
        return new ExperimentDto(getKey(), experiment.getValue());
    }

    protected ExperimentDto newExperimentDto(String value) {
        return new ExperimentDto(getKey(), value);
    }

    protected abstract LocalDateTime getCreatedDate();
}
