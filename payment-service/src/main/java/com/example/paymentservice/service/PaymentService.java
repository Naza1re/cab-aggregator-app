package com.example.paymentservice.service;

import com.example.paymentservice.dto.request.CardRequest;
import com.example.paymentservice.dto.request.ChargeRequest;
import com.example.paymentservice.dto.request.CustomerChargeRequest;
import com.example.paymentservice.dto.request.CustomerRequest;
import com.example.paymentservice.dto.response.*;

public interface PaymentService {

    CustomerResponse createCustomer(CustomerRequest customerRequest);

    CardTokenResponse generateTokenByCard(CardRequest cardRequest);

    StringResponse chargeCard(ChargeRequest chargeRequest);

    CustomerResponse retrieve(Long id);

    ChargeResponse chargeFromCustomer(CustomerChargeRequest chargeRequest);

}
