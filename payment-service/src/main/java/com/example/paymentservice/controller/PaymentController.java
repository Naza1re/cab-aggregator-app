package com.example.paymentservice.controller;

import com.example.paymentservice.dto.request.CardRequest;
import com.example.paymentservice.dto.request.ChargeRequest;
import com.example.paymentservice.dto.request.CustomerChargeRequest;
import com.example.paymentservice.dto.request.CustomerRequest;
import com.example.paymentservice.dto.response.*;
import com.example.paymentservice.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/customer")
    public ResponseEntity<CustomerResponse> createCustomer(
            @Valid @RequestBody CustomerRequest customerRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(paymentService.createCustomer(customerRequest));
    }

    @PostMapping("/charge")
    public ResponseEntity<StringResponse> chargeCard(
            @Valid @RequestBody ChargeRequest chargeRequest) {
        return ResponseEntity.ok(paymentService.chargeCard(chargeRequest));
    }

    @PostMapping("/token")
    public ResponseEntity<TokenResponse> genToken(
            @Valid @RequestBody CardRequest cardRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(paymentService.generateTokenByCard(cardRequest));
    }

    @PostMapping("/customer/charge")
    public ResponseEntity<ChargeResponse> makeChargeFromCustomer(
            @Valid @RequestBody CustomerChargeRequest chargeRequest) {
        return ResponseEntity.ok(paymentService.chargeFromCustomer(chargeRequest));
    }

    @GetMapping("/balance")
    public ResponseEntity<BalanceResponse> getBalance() {
        return ResponseEntity.ok(paymentService.balance());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.retrieve(id));
    }

}
