package com.example.driverservice.kafka;

import com.example.driverservice.dto.request.DriverForRide;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DriverProducer {

    @Value("${topic.name.driver}")
    private String driverTopic;


    private final KafkaTemplate<String, DriverForRide> kafkaTemplate;

    public void sendMessage(DriverForRide driver) {

        log.info(String.format("Message sent -> %s", driver.toString()));
        Message<DriverForRide> message = MessageBuilder
                .withPayload(driver)
                .setHeader(KafkaHeaders.TOPIC, driverTopic)
                .build();
        kafkaTemplate.send(message);
    }
}
