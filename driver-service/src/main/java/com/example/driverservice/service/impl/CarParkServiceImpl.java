package com.example.driverservice.service.impl;

import com.example.driverservice.client.CarParkClient;
import com.example.driverservice.dto.request.CarOwnerRequest;
import com.example.driverservice.dto.response.CarResponse;
import com.example.driverservice.dto.response.DriverRatingListResponse;
import com.example.driverservice.exception.FeignClientException;
import com.example.driverservice.exception.NotFoundException;
import com.example.driverservice.exception.ServiceUnAvailableException;
import com.example.driverservice.service.CarParkService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.example.driverservice.util.ExceptionMessages.RATING_SERVICE_IS_NOT_AVAILABLE;


@Service
@Slf4j
@RequiredArgsConstructor
public class CarParkServiceImpl implements CarParkService {

    private final CarParkClient carParkClient;
    @Override
    @CircuitBreaker(name = "park",fallbackMethod = "fallBackParkService")
    public CarResponse getCarById(Long id) {
        return carParkClient.getCarById(id);
    }

    @Override
    @CircuitBreaker(name = "park",fallbackMethod = "fallBackParkService")
    public CarResponse setOwner(CarOwnerRequest carOwnerRequest) {
        return carParkClient.setOwner(carOwnerRequest);
    }

    private CarResponse fallBackParkService(Exception ex) {
        log.error("Park service is not available. Fallback method called.");
        throw new ServiceUnAvailableException(RATING_SERVICE_IS_NOT_AVAILABLE);
    }

    private void fallBackParkService(FeignClientException ex) {
        log.error("Something went wrong. Fallback method called.");
        throw new FeignClientException(ex.getMessage());
    }

    private void fallBackParkService(NotFoundException ex) {
        log.error("NotFoundException. Fall back method was called.");
        throw new NotFoundException(ex.getMessage());
    }

}
