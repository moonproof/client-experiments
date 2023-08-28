package com.moonproof.configuration;

import com.moonproof.service.ExperimentButtonColorService;
import com.moonproof.service.ExperimentPriceService;
import com.moonproof.service.ExperimentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ComponentScan(basePackages = {"com.moonproof"})
public class ExperimentConfiguration {

    @Bean
    public List<ExperimentService> experimentServiceList(ExperimentPriceService experimentPriceService,
                                                         ExperimentButtonColorService experimentButtonColorService) {
        return List.of(experimentPriceService, experimentButtonColorService);
    }
}
