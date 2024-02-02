package com.example.driverservice.kafka.consumer;

import com.example.driverservice.client.RatingFeignClient;
import com.example.driverservice.dto.request.DriverForRide;
import com.example.driverservice.dto.response.DriverRatingListResponse;
import com.example.driverservice.dto.response.DriverRatingResponse;
import com.example.driverservice.dto.response.RideForDriver;
import com.example.driverservice.kafka.producer.DriverProducer;
import com.example.driverservice.model.Driver;
import com.example.driverservice.repository.DriverRepository;
import com.example.driverservice.service.DriverService;
import com.example.driverservice.util.Constants;
import com.example.driverservice.util.ConstantsMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class RideConsumer {

    private final DriverProducer driverProducer;
    private final RatingFeignClient ratingFeignClient;
    private final DriverRepository driverRepository;
    private final DriverService driverService;

    @KafkaListener(topics = "${topic.name.ride}")
    public void consume(RideForDriver ride) {
        log.info("Json message recieved -> {}", ride);
        driverService.handleDriver(ride.getRideId());
    }



}