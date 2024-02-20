package com.modsen.promocodeservice.exceptions;

public class PromoCodeNotFoundException extends RuntimeException {
    public PromoCodeNotFoundException(String s) {
        super(s);
    }
}
