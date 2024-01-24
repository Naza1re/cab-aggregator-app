package com.example.driverservice.kafka;

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
public class AvailableDriverProducer {

    @Value("${topic.name.available}")
    private String availableTopic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String available) {

        log.info(String.format("Message sent -> %s", available));
        Message<String> message = MessageBuilder
                .withPayload(available)
                .setHeader(KafkaHeaders.TOPIC, availableTopic)
                .build();
        kafkaTemplate.send(message);
    }
}
