package com.example.rideservice.kafka.consumer;

import com.example.rideservice.dto.response.DriverForRide;
import com.example.rideservice.service.RideService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DriverConsumer {
    private final RideService rideService;

    @KafkaListener(topics = "${spring.kafka.topic.name.driver}")
    public void consume(DriverForRide driver) {
        log.info("Json message received -> {}", driver);
        rideService.setDriver(driver);
    }
}
