package com.example.driverservice.kafka;

import com.example.driverservice.dto.response.RideForDriver;
import com.example.driverservice.service.DriverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RideConsumer {

    private final DriverService driverService;

    @KafkaListener(topics = "${topic.name.ride}")
    public void consume(RideForDriver ride) {
        log.info(String.format("Json message recieved -> %s", ride));
        driverService.findDriver(ride.getRideId());
    }


}