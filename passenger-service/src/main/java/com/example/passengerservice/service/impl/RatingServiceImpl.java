package com.example.passengerservice.service.impl;

import com.example.passengerservice.client.RatingFeignClient;
import com.example.passengerservice.dto.request.RatingRequest;
import com.example.passengerservice.exception.FeignClientException;
import com.example.passengerservice.exception.NotFoundException;
import com.example.passengerservice.exception.ServiceUnAvailableException;
import com.example.passengerservice.service.RatingService;
import com.example.passengerservice.util.ExceptionMessages;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RatingServiceImpl implements RatingService {

    private final RatingFeignClient ratingFeignClient;

    @Override
    @CircuitBreaker(name = "rating", fallbackMethod = "fallbackRatingService")
    public void createPassengerRecord(RatingRequest request) {
        ratingFeignClient.createPassengerRecord(request);
    }

    @Override
    @CircuitBreaker(name = "rating", fallbackMethod = "fallbackRatingService")
    public void deletePassengerRecord(Long id) {
    }

    private void fallbackRatingService(NotFoundException ex) {
        throw new NotFoundException(ex.getMessage());
    }

    private void fallbackRatingService(FeignClientException ex) {
        throw new FeignClientException(ex.getMessage());
    }

    private void fallbackRatingService(Exception ex) {
        throw new ServiceUnAvailableException(ExceptionMessages.RATING_SERVICE_IS_NOT_AVAILABLE);
    }
}
