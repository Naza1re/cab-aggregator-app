package com.example.paymentservice.service.impl;

import com.example.paymentservice.client.PassengerFeignClient;
import com.example.paymentservice.exception.FeignClientException;
import com.example.paymentservice.exception.NotFoundException;
import com.example.paymentservice.exception.ServiceUnAvailableException;
import com.example.paymentservice.service.PassengerService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.paymentservice.util.ExceptionMessages.PASSENGER_SERVICE_IS_NOT_AVAILABLE;

@RequiredArgsConstructor
@Service
public class PassengerServiceImpl implements PassengerService {

    private final PassengerFeignClient passengerFeignClient;

    @Override
    @CircuitBreaker(name = "passenger", fallbackMethod = "fallBackPassengerService")
    public void getPassenger(Long id) {
        passengerFeignClient.getPassenger(id);
    }

    private void fallBackPassengerService(FeignClientException ex) {
        throw new FeignClientException(ex.getMessage());
    }

    private void fallBackPassengerService(NotFoundException ex) {
        throw new NotFoundException(ex.getMessage());
    }

    private void fallBackPassengerService(Exception ex) {
        throw new ServiceUnAvailableException(PASSENGER_SERVICE_IS_NOT_AVAILABLE);
    }
}
