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
public class AvailableDriverProducer {

    private final static Logger LOGGER = LoggerFactory.getLogger(DriverProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String available) {

        LOGGER.info(String.format("Message sent -> %s", available));
        Message<String> message = MessageBuilder
                .withPayload(available)
                .setHeader(KafkaHeaders.TOPIC, "available")
                .build();
        kafkaTemplate.send(message);
    }
}
