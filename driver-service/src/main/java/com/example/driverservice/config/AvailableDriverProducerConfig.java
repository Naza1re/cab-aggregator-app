package com.example.driverservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

public class AvailableDriverProducerConfig {
    @Bean
    public NewTopic topic(){
        return TopicBuilder.name("available")
                .partitions(1)
                .replicas(1)
                .build();
    }
}