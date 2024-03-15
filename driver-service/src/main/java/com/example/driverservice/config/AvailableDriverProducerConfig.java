package com.example.driverservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

public class AvailableDriverProducerConfig {

    @Value("${topic.name.available}")
    private String topic;
    @Value("${kafka.partitions.count}")
    private int partitions;
    @Value("${kafka.replicas.count}")
    private int replicas;
    @Bean
    public NewTopic topic(){
        return TopicBuilder.name(topic)
                .partitions(partitions)
                .replicas(replicas)
                .build();
    }
}