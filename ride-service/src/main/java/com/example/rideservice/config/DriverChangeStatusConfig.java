package com.example.rideservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;


@Configuration
public class DriverChangeStatusConfig {

    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name("status")
                .partitions(1)
                .replicas(1)
                .build();
    }


}
