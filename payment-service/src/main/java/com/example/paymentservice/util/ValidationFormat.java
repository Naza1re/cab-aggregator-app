package com.example.paymentservice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationFormat {
    public static final String CARD_FORMAT = "\\d{16}";
    public static final String CVC_FORMAT =  "\\d{3}";
}
