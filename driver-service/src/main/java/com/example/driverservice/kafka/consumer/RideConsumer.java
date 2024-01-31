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

    @KafkaListener(topics = "${topic.name.ride}")
    public void consume(RideForDriver ride) {
        log.info("Json message recieved -> {}", ride);
        handleDriver(ride.getRideId());
    }

    public void handleDriver(Long driverId) {
        DriverForRide driver = findDriverForRide(driverId);
        if (driver == null) {
            log.info(ConstantsMessages.DRIVERS_NOT_AVAILABLE);
        } else {
            driverProducer.sendMessage(driver);
        }

    }

    private DriverForRide findDriverForRide(Long id) {
        DriverRatingListResponse ratingListResponse = ratingFeignClient.getDriversRateList();
        List<Driver> drivers = driverRepository.getAllByAvailable(true);
        Optional<Driver> highestRatedDriver = drivers.stream()
                .filter(Driver::isAvailable)
                .max(Comparator.comparingDouble(driver ->
                        getDriverRating(ratingListResponse, driver.getId())));
        return highestRatedDriver.map(driver -> DriverForRide.builder()
                .rideId(id)
                .driverId(driver.getId())
                .build()
        ).orElse(null);
    }

    private double getDriverRating(DriverRatingListResponse ratingListResponse, Long driverId) {
        return ratingListResponse.getDriverRatingResponseList().stream()
                .filter(ratingResponse -> ratingResponse.getDriver().equals(driverId))
                .mapToDouble(DriverRatingResponse::getRate)
                .findFirst()
                .orElse(Constants.DEFAULT_RATE);
    }


}