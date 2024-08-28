package com.example.rideservice.client;

import com.example.rideservice.config.FeignClientConfiguration;
import com.example.rideservice.dto.request.PriceCalculateRequest;
import com.example.rideservice.dto.response.PriceCalculateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "${service.price.name}",
        path = "${service.price.path}",
        configuration = FeignClientConfiguration.class)
public interface PriceClient {

    @GetMapping
    PriceCalculateResponse priceCalculate(@RequestBody PriceCalculateRequest request);
}
