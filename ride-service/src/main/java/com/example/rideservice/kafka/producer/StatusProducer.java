package com.example.rideservice.kafka.producer;

import com.example.rideservice.dto.request.ChangeDriverStatusRequest;
import com.example.rideservice.dto.request.RideForDriver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class StatusProducer {


    @Value("${spring.kafka.topic.name.status}")
    private String rideTopic;

    private final KafkaTemplate<String, ChangeDriverStatusRequest> kafkaTemplate;

    public void sendMessage(ChangeDriverStatusRequest ride) {

        log.info("Json message send -> {}", ride.toString());
        Message<ChangeDriverStatusRequest> message = MessageBuilder
                .withPayload(ride)
                .setHeader(KafkaHeaders.TOPIC, rideTopic)
                .build();
        kafkaTemplate.send(message);
    }
}
