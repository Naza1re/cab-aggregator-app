package com.example.rideservice.client;

import com.example.rideservice.config.FeignClientConfiguration;
import com.example.rideservice.dto.response.DriverResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(value = "${service.driver.name}",
        path = "${service.driver.path}",
        configuration = FeignClientConfiguration.class)
public interface DriverClient {

    @PutMapping("/{id}/status")
    DriverResponse changeStatus(@PathVariable Long id);


}
