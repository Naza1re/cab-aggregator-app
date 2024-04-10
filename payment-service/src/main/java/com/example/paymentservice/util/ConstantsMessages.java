package com.example.paymentservice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ConstantsMessages {
    public static final String SUCCESSFUL_PAYMENT_WITH_ID = "Successful payment with id ' %s'";
    public static final String CREATING_CUSTOMER = "Creating customer";
    public static final String SAVING_CUSTOMER_IN_DATABASE = "Saving customer in database";
    public static final String TOKEN_GENERATE = "Token : %s was generated";
    public static final String MAKING_CHARGE = "Making charge";
    public static final String CUSTOMER_ALREADY_EXIST = "Customer with passenger id : %s already exist";
    public static final String MAKING_CHARGE_FROM_PASSENGER = "Making charge from customer with passenger id : %s";
}
