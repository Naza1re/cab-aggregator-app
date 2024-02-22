package com.example.rideservice.service.impl;

import com.example.rideservice.client.PromoCodeClient;
import com.example.rideservice.dto.response.PromoCodeResponse;
import com.example.rideservice.exception.NotFoundException;
import com.example.rideservice.exception.ServiceUnAvailableException;
import com.example.rideservice.service.PromoCodeService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.rideservice.util.ExceptionMessages.PROMO_CODE_SERVICE_IS_NOT_AVAILABLE;

@RequiredArgsConstructor
@Service
public class PromoCodeServiceImpl implements PromoCodeService {

    private final PromoCodeClient promoCodeClient;

    @Override
    @CircuitBreaker(name = "promo", fallbackMethod = "fallBackPromoCodeService")
    public PromoCodeResponse getPromCode(String value) {
        return promoCodeClient.getPromoCode(value);
    }

    private PromoCodeResponse fallBackPromoCodeService(NotFoundException ex) {
        throw new NotFoundException(ex.getMessage());
    }

    private PromoCodeResponse fallBackPromoCodeService(Exception ex) {
        throw new ServiceUnAvailableException(PROMO_CODE_SERVICE_IS_NOT_AVAILABLE);
    }
}
