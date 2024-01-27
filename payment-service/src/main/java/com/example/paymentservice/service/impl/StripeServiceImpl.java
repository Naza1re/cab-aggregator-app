package com.example.paymentservice.service.impl;

import com.example.paymentservice.dto.request.CardRequest;
import com.example.paymentservice.dto.request.ChargeRequest;
import com.example.paymentservice.dto.request.CustomerChargeRequest;
import com.example.paymentservice.dto.request.CustomerRequest;
import com.example.paymentservice.exception.BalanceException;
import com.example.paymentservice.exception.CreateCustomerException;
import com.example.paymentservice.exception.GenerateTokenException;
import com.example.paymentservice.exception.PaymentException;
import com.example.paymentservice.model.enums.PaymentMethodEnum;
import com.example.paymentservice.service.StripeService;
import com.example.paymentservice.util.ExceptionMessages;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.net.RequestOptions;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerUpdateParams;
import com.stripe.param.PaymentIntentConfirmParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StripeServiceImpl implements StripeService {

    @Value("${stripe.public}")
    private String publicKey;
    @Value("${stripe.secret}")
    private String secretKey;

    public void createPaymentParams(String customerId) {
        Map<String, Object> paymentParams = new HashMap<>();
        paymentParams.put("type", "card");
        Map<String, Object> cardParams = new HashMap<>();
        cardParams.put("token", "tok_visa");
        paymentParams.put("card", cardParams);
        createPayment(paymentParams, customerId);
    }

    public Customer createStripeCustomerParams(CustomerRequest customerRequest) {
        CustomerCreateParams customerCreateParams = CustomerCreateParams.builder()
                .setPhone(customerRequest.getPhone())
                .setEmail(customerRequest.getEmail())
                .setName(customerRequest.getName())
                .setBalance(customerRequest.getAmount())
                .build();

        return createStripeCustomer(customerCreateParams);
    }

    public Customer createStripeCustomer(CustomerCreateParams customerCreateParams) {
        RequestOptions requestOptions = RequestOptions.builder()
                .setApiKey(secretKey)
                .build();
        try {
            return Customer.create(customerCreateParams, requestOptions);
        } catch (StripeException ex) {
            throw new CreateCustomerException(ex.getMessage());
        }
    }

    public void createPayment(Map<String, Object> paymentParams, String customerId) {
        RequestOptions requestOptions = RequestOptions.builder()
                .setApiKey(secretKey)
                .build();
        try {
            PaymentMethod paymentMethod = PaymentMethod.create(paymentParams);
            paymentMethod.attach(Map.of("customer", customerId), requestOptions);
        } catch (StripeException ex) {
            throw new PaymentException("Error creating payment: " + ex.getMessage());
        }
    }

    public PaymentIntent confirmIntent(CustomerChargeRequest request, String customerId) {
        Stripe.apiKey = secretKey;
        PaymentIntent intent = createIntent(request, customerId);
        PaymentIntentConfirmParams params =
                PaymentIntentConfirmParams.builder()
                        .setPaymentMethod(PaymentMethodEnum.VISA.getVisa())
                        .build();
        return intentParams(intent, params);
    }

    public void changeBalance(String customerId, Long amount) {
        Customer customer = retrieveCustomer(customerId);
        CustomerUpdateParams customerUpdateParams =
                CustomerUpdateParams.builder()
                        .setBalance(customer.getBalance() - amount * 100).build();
        updateCustomer(customerUpdateParams, customer);
    }

    public void checkBalance(String customerId, long amount) {
        Customer customer = retrieveCustomer(customerId);

        Long balance = customer.getBalance();
        if (balance < amount) {
            throw new BalanceException(ExceptionMessages.NOT_ENOUGH_MONEY);
        }
    }

    private PaymentIntent createIntent(CustomerChargeRequest request, String customerId) {
        try {
            PaymentIntent intent = PaymentIntent.create(Map.of("amount", request.getAmount() * 100,
                    "currency", request.getCurrency(),
                    "customer", customerId));
            intent.setPaymentMethod(customerId);
            return intent;
        } catch (StripeException stripeException) {
            throw new PaymentException(stripeException.getMessage());
        }
    }

    public Charge createCharge(ChargeRequest chargeRequest) {
        Map<String, Object> chargeParams = createChargeParams(chargeRequest);
        try {
            RequestOptions requestOptions = RequestOptions.builder()
                    .setApiKey(secretKey)
                    .build();
            return Charge.create(chargeParams, requestOptions);
        } catch (StripeException ex) {
            throw new PaymentException("Error charging card: + " + ex.getMessage());
        }
    }

    private Map<String, Object> createCardParams(CardRequest cardRequest) {
        Map<String, Object> cardParams = new HashMap<>();
        cardParams.put("number", cardRequest.getCardNumber());
        cardParams.put("exp_month", cardRequest.getExpM());
        cardParams.put("exp_year", cardRequest.getExpY());
        cardParams.put("cvc", cardRequest.getCvc());
        return cardParams;
    }

    private Map<String, Object> createChargeParams(ChargeRequest chargeRequest) {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", chargeRequest.getAmount());
        chargeParams.put("currency", chargeRequest.getCurrency());
        chargeParams.put("source", chargeRequest.getToken());
        return chargeParams;
    }

    public Token createToken(CardRequest cardRequest) {

        Map<String, Object> cardParams = createCardParams(cardRequest);
        try {
            RequestOptions requestOptions = RequestOptions.builder()
                    .setApiKey(publicKey)
                    .build();
            return Token.create(Map.of("card", cardParams), requestOptions);
        } catch (StripeException ex) {
            throw new GenerateTokenException("Error generating token: " + ex.getMessage());
        }
    }

    public Customer retrieveCustomer(String customerId) {
        try {
            RequestOptions requestOptions = RequestOptions.builder()
                    .setApiKey(secretKey)
                    .build();
            return Customer.retrieve(customerId, requestOptions);
        } catch (StripeException ex) {
            throw new PaymentException(ex.getMessage());
        }
    }

    public void updateCustomer(CustomerUpdateParams customerUpdateParams, Customer customer) {
        try {
            RequestOptions requestOptions = RequestOptions.builder()
                    .setApiKey(secretKey)
                    .build();
            customer.update(customerUpdateParams, requestOptions);
        } catch (StripeException ex) {
            throw new BalanceException(ex.getMessage());
        }
    }

    public PaymentIntent intentParams(PaymentIntent intent, PaymentIntentConfirmParams params) {
        try {
            RequestOptions requestOptions = RequestOptions.builder()
                    .setApiKey(secretKey)
                    .build();
            return intent.confirm(params,requestOptions);
        } catch (StripeException stripeException) {
            throw new PaymentException(stripeException.getMessage());
        }
    }
}
