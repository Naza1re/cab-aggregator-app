package com.example.driverservice.kafka;

import com.example.driverservice.dto.request.DriverForRide;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverProducer {
    private final static Logger LOGGER = LoggerFactory.getLogger(DriverProducer.class);

    private final KafkaTemplate<String, DriverForRide> kafkaTemplate;

    public void sendMessage(DriverForRide driver) {

        LOGGER.info(String.format("Message sent -> %s", driver.toString()));
        Message<DriverForRide> message = MessageBuilder
                .withPayload(driver)
                .setHeader(KafkaHeaders.TOPIC, "driver")
                .build();
        kafkaTemplate.send(message);
    }
}
