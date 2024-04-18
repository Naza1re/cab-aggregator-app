package com.example.rideservice.service.impl;

import com.example.rideservice.client.PriceClient;
import com.example.rideservice.dto.request.PriceCalculateRequest;
import com.example.rideservice.dto.response.PriceCalculateResponse;
import com.example.rideservice.exception.NotFoundException;
import com.example.rideservice.exception.ServiceUnAvailableException;
import com.example.rideservice.service.PriceService;
import com.example.rideservice.util.ExceptionMessages;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@AllArgsConstructor
public class PriceServiceImpl implements PriceService {

    private final PriceClient priceClient;

    @CircuitBreaker(name = "price", fallbackMethod = "priceCalculateFallBackMethod")
    @Override
    public PriceCalculateResponse getCalculatePrice(PriceCalculateRequest request) {
        return priceClient.priceCalculate(request);
    }

    private PriceCalculateResponse priceCalculateFallBackMethod(Exception ex) {
        log.info("Something went wrong while calling price service. Fallback method was called");
        throw new ServiceUnAvailableException(ExceptionMessages.PRICE_SERVICE_IS_NOT_AVAILABLE);
    }

    private PriceCalculateResponse priceCalculateFallBackMethod(NotFoundException ex) {
        log.info("Something went wrong while calling price service.");
        throw new NotFoundException(ex.getMessage());
    }

}
