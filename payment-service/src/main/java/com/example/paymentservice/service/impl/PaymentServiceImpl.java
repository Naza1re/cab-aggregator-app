package com.example.paymentservice.service.impl;

import com.example.paymentservice.dto.request.CardRequest;
import com.example.paymentservice.dto.request.ChargeRequest;
import com.example.paymentservice.dto.request.CustomerChargeRequest;
import com.example.paymentservice.dto.request.CustomerRequest;
import com.example.paymentservice.dto.response.*;
import com.example.paymentservice.exception.*;
import com.example.paymentservice.model.User;
import com.example.paymentservice.model.enums.PaymentMethodEnum;
import com.example.paymentservice.repository.UserRepository;
import com.example.paymentservice.service.PaymentService;
import com.example.paymentservice.service.StripeService;
import com.example.paymentservice.util.ExceptionMessages;
import com.example.paymentservice.util.PaymentMessages;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerUpdateParams;
import com.stripe.param.PaymentIntentConfirmParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final UserRepository userRepository;
    private final StripeService service;

    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        checkCustomerAlreadyExist(customerRequest.getPassengerId());
        Customer customer = createStripeCustomer(customerRequest);

        createPayment(customer.getId());
        saveUserToDatabase(customer.getId(), customerRequest.getPassengerId());

        return CustomerResponse.builder()
                .id(customer.getId())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .name(customer.getName())
                .build();
    }

    private void saveUserToDatabase(String customerId, Long passengerId) {
        User user = User.builder()
                .customerId(customerId)
                .passengerId(passengerId)
                .build();

        userRepository.save(user);
    }

    private Customer createStripeCustomer(CustomerRequest customerRequest) {
        CustomerCreateParams customerCreateParams = CustomerCreateParams.builder()
                .setPhone(customerRequest.getPhone())
                .setEmail(customerRequest.getEmail())
                .setName(customerRequest.getName())
                .setBalance(customerRequest.getAmount())
                .build();

        return service.createStripeCustomer(customerCreateParams);
    }

    private void createPayment(String customerId) {
        Map<String, Object> paymentParams = new HashMap<>();
        paymentParams.put("type", "card");

        Map<String, Object> cardParams = new HashMap<>();
        cardParams.put("token", "tok_visa");
        paymentParams.put("card", cardParams);
        service.createPayment(paymentParams, customerId);
    }

    public CardTokenResponse generateTokenByCard(CardRequest cardRequest) {
        Map<String, Object> cardParams = createCardParams(cardRequest);
        Token token = service.createToken(cardParams);
        return new CardTokenResponse(token.getId());
    }

    private Map<String, Object> createCardParams(CardRequest cardRequest) {
        Map<String, Object> cardParams = new HashMap<>();
        cardParams.put("number", cardRequest.getCardNumber());
        cardParams.put("exp_month", cardRequest.getExpM());
        cardParams.put("exp_year", cardRequest.getExpY());
        System.out.println(cardRequest.getExpY());
        cardParams.put("cvc", cardRequest.getCvc());
        return cardParams;
    }

    public StringResponse chargeCard(ChargeRequest chargeRequest) {
        Map<String, Object> chargeParams = createChargeParams(chargeRequest);
        Charge charge = service.createCharge(chargeParams);
        return new StringResponse(String.format(PaymentMessages.SUCCESSFUL_PAYMENT_WITH_ID, charge.getId()));
    }

    private Map<String, Object> createChargeParams(ChargeRequest chargeRequest) {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", chargeRequest.getAmount());
        chargeParams.put("currency", chargeRequest.getCurrency());
        chargeParams.put("source", chargeRequest.getToken());
        return chargeParams;
    }

    public CustomerResponse retrieve(Long id) {
        User user = getCustomerById(id);
        String customerId = user.getCustomerId();
        Customer customer = service.retrieveCustomer(customerId);
        return CustomerResponse.builder()
                .id(customer.getId())
                .email(customer.getEmail())
                .name(customer.getName())
                .phone(customer.getPhone())
                .build();
    }

    private void checkCustomerAlreadyExist(long passengerId) {
        if (userRepository.existsById(passengerId)) {
            throw new CustomerAlreadyExistException(String.format(ExceptionMessages.CUSTOMER_ALREADY_EXIST, passengerId));
        }
    }

    private User getCustomerById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(String.format(ExceptionMessages.CUSTOMER_NOT_FOUND_EXCEPTION, id)));
    }

    public ChargeResponse chargeFromCustomer(CustomerChargeRequest chargeRequest) {

        User user = getCustomerById(chargeRequest.getPassengerId());

        String customerId = user.getCustomerId();
        checkBalance(customerId, chargeRequest.getAmount());

        PaymentIntent paymentIntent = confirmIntent(chargeRequest, customerId);
        changeBalance(customerId, chargeRequest.getAmount());

        return ChargeResponse.builder()
                .id(paymentIntent.getId())
                .amount(paymentIntent.getAmount() / 100)
                .currency(paymentIntent.getCurrency()).build();
    }

    private PaymentIntent confirmIntent(CustomerChargeRequest request, String customerId) {
        PaymentIntent intent = createIntent(request, customerId);
        PaymentIntentConfirmParams params =
                PaymentIntentConfirmParams.builder()
                        .setPaymentMethod(PaymentMethodEnum.VISA.getVisa())
                        .build();
        return service.intentParams(intent, params);
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

    private void checkBalance(String customerId, long amount) {
        Customer customer = service.retrieveCustomer(customerId);

        Long balance = customer.getBalance();
        if (balance < amount) {
            throw new BalanceException(ExceptionMessages.NOT_ENOUGH_MONEY);
        }
    }

    private void changeBalance(String customerId, Long amount) {
        Customer customer = service.retrieveCustomer(customerId);
        CustomerUpdateParams customerUpdateParams =
                CustomerUpdateParams.builder()
                        .setBalance(customer.getBalance() - amount * 100).build();
        service.updateCustomer(customerUpdateParams, customer);
    }

}
