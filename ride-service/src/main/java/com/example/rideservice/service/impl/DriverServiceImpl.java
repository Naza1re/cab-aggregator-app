package com.example.rideservice.service.impl;

import com.example.rideservice.client.DriverClient;
import com.example.rideservice.exception.ServiceUnAvailableException;
import com.example.rideservice.service.DriverService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.rideservice.util.ExceptionMessages.DRIVER_SERVICE_IS_NOT_AVAILABLE;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {
    private final DriverClient driverClient;

    @Override
    @CircuitBreaker(name = "driver", fallbackMethod = "fallBackDriverService")
    public void changeStatus(Long id) {
        driverClient.changeStatus(id);
    }

    private void fallBackDriverService(Exception ex) {
        throw new ServiceUnAvailableException(DRIVER_SERVICE_IS_NOT_AVAILABLE);
    }
}
