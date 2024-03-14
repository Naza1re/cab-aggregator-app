package com.example.driverservice.config;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

@Configuration
public class DriverProducerConfig {
    @Bean
    public NewTopic topic(){
        return TopicBuilder.name("driver")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public StringJsonMessageConverter stringJsonMessageConverter(){
        return new StringJsonMessageConverter();
    }
}