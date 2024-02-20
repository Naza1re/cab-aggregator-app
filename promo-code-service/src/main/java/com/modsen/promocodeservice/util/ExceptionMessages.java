package com.modsen.promocodeservice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionMessages {

    public final static String PROMOCODE_NOT_FOUND = "PromoCode with id '%s' not found";
    public final static String PROMOCODE_ALREADY_EXIST = "PromoCode with value '%s' already exist";
}
