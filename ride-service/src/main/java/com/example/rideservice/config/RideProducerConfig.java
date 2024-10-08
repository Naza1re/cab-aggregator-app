package com.example.rideservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

@Configuration
public class RideProducerConfig {

    @Value("${kafka.partitions.count}")
    private int partitions;
    @Value("${kafka.replicas.count}")
    private int replicas;

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name("ride")
                .partitions(partitions)
                .replicas(replicas)
                .build();
    }

    @Bean
    public StringJsonMessageConverter stringJsonMessageConverter() {
        return new StringJsonMessageConverter();
    }
}