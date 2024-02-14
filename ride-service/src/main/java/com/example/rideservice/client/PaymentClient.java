package com.example.rideservice.client;

import com.example.rideservice.config.FeignClientConfiguration;
import com.example.rideservice.dto.request.CustomerChargeRequest;
import com.example.rideservice.dto.response.ChargeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "${service.payment.name}",
        path = "${service.payment.path}",
        configuration = FeignClientConfiguration.class)
public interface PaymentClient {
    @GetMapping("/customer/charge")
    ChargeResponse chargeFromCustomer(@RequestBody CustomerChargeRequest request);
}