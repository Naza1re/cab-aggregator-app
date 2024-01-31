package com.example.paymentservice.client;

import com.example.paymentservice.config.FeignClientConfiguration;
import com.example.paymentservice.dto.response.PassengerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "${feign.client.passenger.name}", url = "${feign.client.passenger.url}",
        path = "${feign.client.passenger.path}", configuration = FeignClientConfiguration.class)
public interface PassengerFeignClient {
    @GetMapping("/{id}")
    PassengerResponse getPassenger(@PathVariable Long id);

}

