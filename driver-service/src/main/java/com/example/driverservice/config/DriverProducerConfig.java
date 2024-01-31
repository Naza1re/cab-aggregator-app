package com.example.driverservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

@Configuration
public class DriverProducerConfig {
    @Value("${topic.name.driver}")
    private String driver;
    @Value("${kafka.partitions.count}")
    private int partitionCount;
    @Value("${kafka.replicas.count}")
    private int replicasCount;

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name(driver)
                .partitions(partitionCount)
                .replicas(replicasCount)
                .build();
    }

    @Bean
    public StringJsonMessageConverter stringJsonMessageConverter() {
        return new StringJsonMessageConverter();
    }
}
