package com.example.rideservice.kafka;

import com.example.rideservice.dto.request.RideForDriver;
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
public class RideProducer {

    private final static Logger LOGGER = LoggerFactory.getLogger(RideProducer.class);

    private final KafkaTemplate<String, RideForDriver> kafkaTemplate;

    public void sendMessage(RideForDriver ride) {

        LOGGER.info(String.format("Message sent -> %s", ride.toString()));
        Message<RideForDriver> message = MessageBuilder
                .withPayload(ride)
                .setHeader(KafkaHeaders.TOPIC, "ride")
                .build();
        kafkaTemplate.send(message);
    }

}
