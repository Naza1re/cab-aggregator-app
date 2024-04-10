package com.example.passengerservice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationFormat {

    public static final String PHONE_REGEX = "\\d{11}";
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

}
