package com.example.paymentservice.service.impl;

import com.example.paymentservice.dto.request.CardRequest;
import com.example.paymentservice.dto.request.ChargeRequest;
import com.example.paymentservice.dto.request.CustomerChargeRequest;
import com.example.paymentservice.dto.request.CustomerRequest;
import com.example.paymentservice.dto.response.CardTokenResponse;
import com.example.paymentservice.dto.response.ChargeResponse;
import com.example.paymentservice.dto.response.CustomerResponse;
import com.example.paymentservice.dto.response.StringResponse;
import com.example.paymentservice.exception.CustomerAlreadyExistException;
import com.example.paymentservice.exception.CustomerNotFoundException;
import com.example.paymentservice.model.User;
import com.example.paymentservice.repository.UserRepository;
import com.example.paymentservice.service.PassengerService;
import com.example.paymentservice.service.PaymentService;
import com.example.paymentservice.service.StripeService;
import com.example.paymentservice.util.ExceptionMessages;
import com.example.paymentservice.util.ConstantsMessages;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.example.paymentservice.util.ConstantsMessages.*;


@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final UserRepository userRepository;
    private final StripeService service;
    private final PassengerService passengerService;

    @Override
    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        checkCustomerAlreadyExist(customerRequest.getPassengerId());
        Customer customer = service.createStripeCustomerParams(customerRequest);
        passengerService.getPassenger(customerRequest.getPassengerId());

        service.createPaymentParams(customer.getId());
        saveUserToDatabase(customer.getId(), customerRequest.getPassengerId());

        log.info(CREATING_CUSTOMER);
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

        log.info(SAVING_CUSTOMER_IN_DATABASE);
        userRepository.save(user);
    }

    @Override
    public CardTokenResponse generateTokenByCard(CardRequest cardRequest) {
        Token token = service.createToken(cardRequest);

        log.info(String.format(TOKEN_GENERATE, token));
        return new CardTokenResponse(token.getId());
    }

    @Override
    public StringResponse chargeCard(ChargeRequest chargeRequest) {
        Charge charge = service.createCharge(chargeRequest);
        log.info(MAKING_CHARGE);
        return new StringResponse(String.format(ConstantsMessages.SUCCESSFUL_PAYMENT_WITH_ID, charge.getId()));
    }

    @Override
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
        if (userRepository.existsByPassengerId(passengerId)) {
            log.info(CUSTOMER_ALREADY_EXIST);
            throw new CustomerAlreadyExistException(String.format(ExceptionMessages.CUSTOMER_ALREADY_EXIST, passengerId));
        }
    }

    private User getCustomerById(Long id) {
        return userRepository.findByPassengerId(id)
                .orElseThrow(() -> {
                    log.info(String.format(CUSTOMER_ALREADY_EXIST, id));
                    return new CustomerNotFoundException(String.format(ExceptionMessages.CUSTOMER_NOT_FOUND_EXCEPTION, id));
                });
    }

    @Override
    public ChargeResponse chargeFromCustomer(CustomerChargeRequest chargeRequest) {

        User user = getCustomerById(chargeRequest.getPassengerId());

        String customerId = user.getCustomerId();
        service.checkBalance(customerId, chargeRequest.getAmount());
        PaymentIntent paymentIntent = service.confirmIntent(chargeRequest, customerId);
        service.changeBalance(customerId, chargeRequest.getAmount());

        log.info(String.format(MAKING_CHARGE_FROM_PASSENGER, chargeRequest.getPassengerId()));
        return ChargeResponse.builder()
                .id(paymentIntent.getId())
                .amount(paymentIntent.getAmount() / 100)
                .currency(paymentIntent.getCurrency()).build();
    }

}
