package com.example.rideservice.service.impl;

import com.example.rideservice.client.PassengerClient;
import com.example.rideservice.dto.response.PassengerResponse;
import com.example.rideservice.exception.FeignClientException;
import com.example.rideservice.exception.NotFoundException;
import com.example.rideservice.exception.ServiceUnAvailableException;
import com.example.rideservice.service.PassengerService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.rideservice.util.ExceptionMessages.PASSENGER_SERVICE_IS_NOT_AVAILABLE;
import static com.example.rideservice.util.ExceptionMessages.PROMO_CODE_SERVICE_IS_NOT_AVAILABLE;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private final PassengerClient passengerClient;

    @Override
    @CircuitBreaker(name = "passenger", fallbackMethod = "fallBackPassengerService")
    public PassengerResponse getPassenger(Long id) {
        return passengerClient.getPassenger(id);
    }

    private PassengerResponse fallBackPassengerService(Exception ex) {
        throw new ServiceUnAvailableException(PASSENGER_SERVICE_IS_NOT_AVAILABLE);
    }

    private void fallBackPaymentService(FeignClientException ex) {
        throw new FeignClientException(ex.getMessage());
    }

    private PassengerResponse fallBackPassengerService(NotFoundException ex) {
        throw new NotFoundException(ex.getMessage());
    }
}
