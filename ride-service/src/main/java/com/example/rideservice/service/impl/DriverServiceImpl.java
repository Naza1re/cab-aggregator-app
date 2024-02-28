package com.example.rideservice.service.impl;

import com.example.rideservice.client.DriverClient;
import com.example.rideservice.exception.FeignClientException;
import com.example.rideservice.exception.NotFoundException;
import com.example.rideservice.exception.ServiceUnAvailableException;
import com.example.rideservice.service.DriverService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.example.rideservice.util.ExceptionMessages.DRIVER_SERVICE_IS_NOT_AVAILABLE;

@Service
@Slf4j
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {
    private final DriverClient driverClient;

    @Override
    @CircuitBreaker(name = "driver", fallbackMethod = "fallBackDriverService")
    public void changeStatus(Long id) {
        driverClient.changeStatus(id);
    }

    private void fallBackDriverService(Exception ex) {
        log.info("Driver service is not available. Fallback method was called");
        throw new ServiceUnAvailableException(DRIVER_SERVICE_IS_NOT_AVAILABLE);
    }

    private void fallBackPaymentService(FeignClientException ex) {
        log.info("Something went wrong. Fallback method was called");
        throw new FeignClientException(ex.getMessage());
    }

    private void fallBackDriverService(NotFoundException ex) {
        log.info("Driver not found. Fallback method was called");
        throw new NotFoundException(ex.getMessage());
    }
}
