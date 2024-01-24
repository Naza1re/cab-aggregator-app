package com.example.rideservice.kafka;

import com.example.rideservice.dto.request.RideForDriver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RideProducer {

    @Value("${topic.name.ride}")
    private String rideTopic;

    private final KafkaTemplate<String, RideForDriver> kafkaTemplate;

    public void sendMessage(RideForDriver ride) {

        log.info(String.format("Message sent -> %s", ride.toString()));
        Message<RideForDriver> message = MessageBuilder
                .withPayload(ride)
                .setHeader(KafkaHeaders.TOPIC, rideTopic)
                .build();
        kafkaTemplate.send(message);
    }

}
