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
import com.example.paymentservice.util.ExceptionMessages;
import com.example.paymentservice.util.PaymentMessages;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
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
public class PaymentServiceImpl implements PaymentService {

    private final UserRepository userRepository;

    @Value("${stripe.secret}")
    private String SECRET_KEY;

    @Value("${stripe.public}")
    private String PUBLIC_KEY;

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
        try {
            Stripe.apiKey=PUBLIC_KEY;
            CustomerCreateParams customerCreateParams = CustomerCreateParams.builder()
                    .setPhone(customerRequest.getPhone())
                    .setEmail(customerRequest.getEmail())
                    .setName(customerRequest.getName())
                    .setBalance(customerRequest.getAmount())
                    .build();

            Stripe.apiKey = SECRET_KEY;

            return Customer.create(customerCreateParams);
        } catch (StripeException ex) {
            throw new CreateCustomerException(ex.getMessage());
        } finally {
            Stripe.apiKey = SECRET_KEY;
        }
    }

    private void createPayment(String customerId) {
        try {
            Stripe.apiKey = SECRET_KEY;

            Map<String, Object> paymentParams = new HashMap<>();
            paymentParams.put("type", "card");

            Map<String, Object> cardParams = new HashMap<>();
            cardParams.put("token", "tok_visa");
            paymentParams.put("card", cardParams);

            PaymentMethod paymentMethod = PaymentMethod.create(paymentParams);
            paymentMethod.attach(Map.of("customer", customerId));
        } catch (StripeException ex) {
            throw new PaymentException("Error creating payment: " + ex.getMessage());
        } finally {
            Stripe.apiKey = SECRET_KEY;
        }
    }

    public TokenResponse generateTokenByCard(CardRequest cardRequest) {
        try {
            Stripe.apiKey = PUBLIC_KEY;
            Map<String, Object> cardParams = createCardParams(cardRequest);
            Token token = createToken(cardParams);
            return new TokenResponse(token.getId());
        } catch (StripeException ex) {
            throw new GenerateTokenException("Error generating token: " + ex.getMessage());
        } finally {
            Stripe.apiKey = SECRET_KEY;
        }
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

    private Token createToken(Map<String, Object> cardParams) throws StripeException {
        return Token.create(Map.of("card", cardParams));
    }

    public StringResponse chargeCard(ChargeRequest chargeRequest) {
        try {
            Stripe.apiKey = SECRET_KEY;
            Map<String, Object> chargeParams = createChargeParams(chargeRequest);
            Charge charge = createCharge(chargeParams);
            return new StringResponse(String.format(PaymentMessages.SUCCESSFUL_PAYMENT_WITH_ID,charge.getId()));
        } catch (StripeException ex) {
            throw new PaymentException("Error charging card: " + ex.getMessage());
        } finally {
            Stripe.apiKey = SECRET_KEY;
        }
    }

    private Charge createCharge(Map<String, Object> chargeParams) throws StripeException {
        return Charge.create(chargeParams);
    }

    private Map<String, Object> createChargeParams(ChargeRequest chargeRequest) {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", chargeRequest.getAmount());
        chargeParams.put("currency", chargeRequest.getCurrency());
        chargeParams.put("source", chargeRequest.getToken());
        return chargeParams;
    }

    public CustomerResponse retrieve(Long id){
        Stripe.apiKey=SECRET_KEY;
        User user = getCustomerById(id);
        String customerId = user.getCustomerId();
        Customer customer;
        try{
            customer = Customer.retrieve(customerId);
        }catch (StripeException ex){
            throw new PaymentException(ex.getMessage());
        }
        return CustomerResponse.builder()
                .id(customer.getId())
                .email(customer.getEmail())
                .name(customer.getName())
                .phone(customer.getPhone())
                .build();
    }

    private void checkCustomerAlreadyExist(long passengerId) {
        if(userRepository.existsById(passengerId)){
            throw new CustomerAlreadyExistException(String.format(ExceptionMessages.CUSTOMER_ALREADY_EXIST,passengerId));
        }
    }

    private User getCustomerById(Long id){
        return userRepository.findById(id)
                .orElseThrow(()->new CustomerNotFoundException(String.format(ExceptionMessages.CUSTOMER_NOT_FOUND_EXCEPTION,id)));
    }

    public BalanceResponse balance() {
        Stripe.apiKey=SECRET_KEY;
        Balance balance;
        try{
            balance=Balance.retrieve();
        }catch (StripeException ex){
            throw new PaymentException(ex.getMessage());
        }
        return BalanceResponse.builder()
                .amount(balance.getPending().get(0).getAmount())
                .currency(balance.getPending().get(0).getCurrency())
                .build();

    }
    private Customer retrieveCustomer(String customerId) {
        try {
            return Customer.retrieve(customerId);
        } catch (StripeException stripeException) {
            throw new PaymentException(stripeException.getMessage());
        }
    }

    public ChargeResponse chargeFromCustomer(CustomerChargeRequest chargeRequest) {
        Stripe.apiKey=SECRET_KEY;

        User user = getCustomerById(chargeRequest.getPassengerId());

        String customerId = user.getCustomerId();
        checkBalance(customerId, chargeRequest.getAmount());

        PaymentIntent paymentIntent = confirmIntent(chargeRequest, customerId);
        changeBalance(customerId, chargeRequest.getAmount());

        return ChargeResponse.builder()
                .amount(paymentIntent.getAmount()/100)
                .currency(paymentIntent.getCurrency()).build();
    }

    private PaymentIntent confirmIntent(CustomerChargeRequest request, String customerId) {
        PaymentIntent intent = createIntent(request, customerId);
        PaymentIntentConfirmParams params =
                PaymentIntentConfirmParams.builder()
                        .setPaymentMethod(PaymentMethodEnum.VISA.getVisa())
                        .build();
        try {
            return intent.confirm(params);
        } catch (StripeException stripeException) {
            throw new PaymentException(stripeException.getMessage());
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

    private void checkBalance(String customerId, long amount) {
        Customer customer = retrieveCustomer(customerId);

        Long balance = customer.getBalance();
        if (balance < amount) {
            throw new BalanceException(ExceptionMessages.NOT_ENOUGH_MONEY);
        }
    }

    private void changeBalance(String customerId,Long amount) {
        Customer customer = retrieveCustomer(customerId);
        CustomerUpdateParams customerUpdateParams =
                CustomerUpdateParams.builder()
                        .setBalance(customer.getBalance()-amount*100).build();
        Stripe.apiKey=SECRET_KEY;
        try {
            customer.update(customerUpdateParams);
        }catch (StripeException ex){
            throw new BalanceException(ex.getMessage());
        }
    }

}
