package com.example.rideservice.client;

import com.example.rideservice.config.FeignClientConfiguration;
import com.example.rideservice.dto.response.PromoCodeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "${service.promo.name}",
        path = "${service.promo.path}",
        configuration = FeignClientConfiguration.class)
public interface PromoCodeClient {
    @GetMapping("/value/{value}")
    PromoCodeResponse getPromoCode(@PathVariable String value);
}
