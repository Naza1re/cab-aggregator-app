package com.example.rideservice.client;

import com.example.rideservice.config.FeignClientConfiguration;
import com.example.rideservice.dto.response.PassengerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "${service.passenger.name}",
        path = "${service.passenger.path}",
        configuration = FeignClientConfiguration.class)
public interface PassengerClient {
    @GetMapping("/{id}")
    PassengerResponse getPassenger(@PathVariable Long id);
}
