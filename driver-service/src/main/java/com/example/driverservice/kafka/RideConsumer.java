package com.example.driverservice.kafka;

import com.example.driverservice.dto.response.RideForDriver;
import com.example.driverservice.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RideConsumer {
    private final DriverService driverService;

    private static final Logger LOGGER = LoggerFactory.getLogger(RideConsumer.class);

    @KafkaListener(topics = "ride")
    public void consume(RideForDriver ride) {
        LOGGER.info(String.format("Json message recieved -> %s", ride));
        driverService.findDriver(ride.getRideId());
    }


}