package com.example.rideservice.service.impl;

import com.example.rideservice.client.PaymentClient;
import com.example.rideservice.dto.request.CustomerChargeRequest;
import com.example.rideservice.exception.FeignClientException;
import com.example.rideservice.exception.NotFoundException;
import com.example.rideservice.exception.ServiceUnAvailableException;
import com.example.rideservice.service.PaymentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.example.rideservice.util.ExceptionMessages.PAYMENT_SERVICE_IS_NOT_AVAILABLE;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentClient paymentClient;

    @Override
    @CircuitBreaker(name = "payment", fallbackMethod = "fallBackPaymentService")
    public void chargeFromCustomer(CustomerChargeRequest request) {
        paymentClient.chargeFromCustomer(request);
    }

    private void fallBackPaymentService(FeignClientException ex) {
        log.info("Something went wrong. Fallback method was called");
        throw new FeignClientException(ex.getMessage());
    }

    private void fallBackPaymentService(Exception ex) {
        log.info("Payment service is not available. Fall back method was called");
        throw new ServiceUnAvailableException(PAYMENT_SERVICE_IS_NOT_AVAILABLE);
    }

    private void fallBackPaymentService(NotFoundException ex) {
        log.info("Customer not found. Fall back method was called");
        throw new NotFoundException(ex.getMessage());
    }
}
