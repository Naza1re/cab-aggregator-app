package org.example.hiringdriverservice.config;

import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.springframework.context.annotation.Bean;


public class CamundaConfiguration {

    @Bean
    public SpringProcessEngineConfiguration processEngineConfiguration() {
        SpringProcessEngineConfiguration config = new SpringProcessEngineConfiguration();
        config.setHistoryTimeToLive("30");
        return config;
    }
}
