package com.example.driverservice.client;

import com.example.driverservice.config.FeignClientConfiguration;
import com.example.driverservice.dto.request.CarOwnerRequest;
import com.example.driverservice.dto.response.CarResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "${service.park.name}",
        path = "${service.park.path}",
        configuration = FeignClientConfiguration.class)
public interface CarParkClient {

    @GetMapping("/{id}")
    CarResponse getCarById(@PathVariable("id") Long id);

    @PutMapping("/set/owner")
    CarResponse setOwner(@RequestBody CarOwnerRequest carOwnerRequest);
}
