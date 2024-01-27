package com.example.driverservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

public class AvailableDriverProducerConfig {
    @Value("${topic.name.available}")
    private String available;
    @Value("${kafka.partitions.count}")
    private int PARTITIONS_COUNT;
    @Value("${kafka.replicas.count}")
    private int REPLICAS_COUNT;

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name(available)
                .partitions(PARTITIONS_COUNT)
                .replicas(REPLICAS_COUNT)
                .build();
    }
}
