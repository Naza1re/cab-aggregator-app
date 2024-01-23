package com.example.paymentservice.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentMethodEnum {

    VISA("pm_card_visa");
    private String visa;


}
