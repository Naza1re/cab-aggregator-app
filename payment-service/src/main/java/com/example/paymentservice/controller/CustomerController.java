package com.example.paymentservice.controller;

import com.example.paymentservice.dto.request.CustomerChargeRequest;
import com.example.paymentservice.dto.request.CustomerRequest;
import com.example.paymentservice.dto.response.ChargeResponse;
import com.example.paymentservice.dto.response.CustomerResponse;
import com.example.paymentservice.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment/customer")
public class CustomerController {

    private final PaymentService paymentService;
    @PreAuthorize("hasAnyRole('ROLE_PASSENGER')")
    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(
            @Valid @RequestBody CustomerRequest customerRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(paymentService.createCustomer(customerRequest));
    }

    @PreAuthorize("hasAnyRole('ROLE_PASSENGER')")
    @PostMapping("/charge")
    public ResponseEntity<ChargeResponse> makeChargeFromCustomer(
            @Valid @RequestBody CustomerChargeRequest chargeRequest) {
        return ResponseEntity.ok(paymentService.chargeFromCustomer(chargeRequest));
    }
}
