package com.example.paymentservice.service;

import com.example.paymentservice.dto.request.CardRequest;
import com.example.paymentservice.dto.request.ChargeRequest;
import com.example.paymentservice.dto.request.CustomerChargeRequest;
import com.example.paymentservice.dto.request.CustomerRequest;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Token;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerUpdateParams;
import com.stripe.param.PaymentIntentConfirmParams;

import java.util.Map;

public interface StripeService {

    void createPaymentParams(String customerId);

    Customer createStripeCustomerParams(CustomerRequest customerRequest);

    Customer createStripeCustomer(CustomerCreateParams customerCreateParams);

    void createPayment(Map<String, Object> paymentParams, String customerId);

    PaymentIntent confirmIntent(CustomerChargeRequest request, String customerId);

    void changeBalance(String customerId, Long amount);

    void checkBalance(String customerId, long amount);

    Charge createCharge(ChargeRequest chargeRequest);

    Token createToken(CardRequest cardRequest);

    Customer retrieveCustomer(String customerId);

    void updateCustomer(CustomerUpdateParams customerUpdateParams, Customer customer);

    PaymentIntent intentParams(PaymentIntent intent, PaymentIntentConfirmParams params);
}
