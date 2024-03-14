package com.example.rideservice.kafka.consumer;

import com.example.rideservice.service.RideService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewAvailableDriverConsumer {
    private final RideService rideService;

    @KafkaListener(topics = "${spring.kafka.topic.name.available}")
    public void consume(String message) {
        log.info("Json message recieved -> {}", message);
        rideService.handleRideForAvailableDriver();
    }
}
