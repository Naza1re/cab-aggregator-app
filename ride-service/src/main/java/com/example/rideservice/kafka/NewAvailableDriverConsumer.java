package com.example.rideservice.kafka;

import com.example.rideservice.service.RideService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class NewAvailableDriverConsumer {
    private final RideService rideService;

    @KafkaListener(topics = "${topic.name.available}")
    public void consume(String message) {
        log.info(String.format("Json message recieved -> %s", message));
        rideService.findRideForAvailableDriver();
    }
}
