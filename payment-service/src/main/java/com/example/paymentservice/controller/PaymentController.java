package com.example.paymentservice.controller;

import com.example.paymentservice.dto.request.CardRequest;
import com.example.paymentservice.dto.request.ChargeRequest;
import com.example.paymentservice.dto.response.CardTokenResponse;
import com.example.paymentservice.dto.response.CustomerResponse;
import com.example.paymentservice.dto.response.StringResponse;
import com.example.paymentservice.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @PreAuthorize("hasAnyRole('ROLE_PASSENGER')")
    @PostMapping("/charge")
    public ResponseEntity<StringResponse> chargeCard(
            @Valid @RequestBody ChargeRequest chargeRequest) {
        return ResponseEntity.ok(paymentService.chargeCard(chargeRequest));
    }

    @PreAuthorize("hasAnyRole('ROLE_PASSENGER')")
    @PostMapping("/token")
    public ResponseEntity<CardTokenResponse> genToken(
            @Valid @RequestBody CardRequest cardRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(paymentService.generateTokenByCard(cardRequest));
    }


    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.retrieve(id));
    }

}
