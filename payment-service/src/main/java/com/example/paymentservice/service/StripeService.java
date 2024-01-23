package com.example.paymentservice.service;

import com.example.paymentservice.exception.BalanceException;
import com.example.paymentservice.exception.CreateCustomerException;
import com.example.paymentservice.exception.GenerateTokenException;
import com.example.paymentservice.exception.PaymentException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerUpdateParams;
import com.stripe.param.PaymentIntentConfirmParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class StripeService {

    @Value("${stripe.secret}")
    private String SECRET_KEY;

    @Value("${stripe.public}")
    private String PUBLIC_KEY;

    public Customer createStripeCustomer(CustomerCreateParams customerCreateParams) {
        try {
            Stripe.apiKey = SECRET_KEY;
            return Customer.create(customerCreateParams);
        } catch (StripeException ex) {
            throw new CreateCustomerException(ex.getMessage());
        } finally {
            Stripe.apiKey = SECRET_KEY;
        }
    }

    public void createPayment(Map<String, Object> paymentParams, String customerId) {
        try {
            Stripe.apiKey = SECRET_KEY;
            PaymentMethod paymentMethod = PaymentMethod.create(paymentParams);
            paymentMethod.attach(Map.of("customer", customerId));
        } catch (StripeException ex) {
            throw new PaymentException("Error creating payment: " + ex.getMessage());
        } finally {
            Stripe.apiKey = SECRET_KEY;
        }
    }

    public Charge createCharge(Map<String, Object> chargeParams) {
        try {
            Stripe.apiKey = SECRET_KEY;
            return Charge.create(chargeParams);
        } catch (StripeException ex) {
            throw new PaymentException("Error charging card: + " + ex.getMessage());
        } finally {
            Stripe.apiKey = SECRET_KEY;
        }

    }

    public Token createToken(Map<String, Object> cardParams) {
        try {
            Stripe.apiKey = PUBLIC_KEY;
            return Token.create(Map.of("card", cardParams));
        } catch (StripeException ex) {
            throw new GenerateTokenException("Error generating token: " + ex.getMessage());
        } finally {
            Stripe.apiKey = SECRET_KEY;
        }
    }

    public Customer retrieveCustomer(String customerId) {
        try {
            Stripe.apiKey = SECRET_KEY;
            return Customer.retrieve(customerId);
        } catch (StripeException ex) {
            throw new PaymentException(ex.getMessage());
        } finally {
            Stripe.apiKey = SECRET_KEY;
        }
    }

    public void updateCustomer(CustomerUpdateParams customerUpdateParams, Customer customer) {
        Stripe.apiKey = SECRET_KEY;
        try {
            customer.update(customerUpdateParams);
        } catch (StripeException ex) {
            throw new BalanceException(ex.getMessage());
        } finally {
            Stripe.apiKey = SECRET_KEY;
        }
    }

    public PaymentIntent intentParams(PaymentIntent intent, PaymentIntentConfirmParams params) {
        try {
            return intent.confirm(params);
        } catch (StripeException stripeException) {
            throw new PaymentException(stripeException.getMessage());
        }
    }
}
