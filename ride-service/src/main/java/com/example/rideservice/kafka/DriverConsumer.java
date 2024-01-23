package com.example.rideservice.kafka;

import com.example.rideservice.dto.response.DriverForRide;
import com.example.rideservice.service.RideService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverConsumer {
    private final RideService rideService;
    private static final Logger LOGGER = LoggerFactory.getLogger(DriverConsumer.class);

    @KafkaListener(topics = "driver")
    public void consume(DriverForRide driver) {
        LOGGER.info(String.format("Json message recieved -> %s", driver));
        rideService.setDriver(driver);
    }
}
