package com.modsen.apigateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackController {

    @RequestMapping("/fallback/passenger-service")
    public String passengerServiceFallback() {
        return "Passenger service is down. Please try again later.";
    }

    @RequestMapping("/fallback/drivers-service")
    public String driverServiceFallback() {
        return "Driver service is down. Please try again later.";
    }

    @RequestMapping("/fallback/rides-service")
    public String ridesServiceFallback() {
        return "Rides service is down. Please try again later.";
    }

    @RequestMapping("/fallback/promo-code-service")
    public String promoCodeServiceFallback() {
        return "Promo-code service is down. Please try again later.";
    }

    @RequestMapping("/fallback/rating-service")
    public String paymentServiceFallback() {
        return "Rating service is down. Please try again later.";
    }
}
