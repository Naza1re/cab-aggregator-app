package com.example.paymentservice.dto.request;

import com.example.paymentservice.util.ValidationFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardRequest {
    @Pattern(regexp = ValidationFormat.CARD_FORMAT, message = "{card.number.pattern}")
    private String cardNumber;
    @NotNull(message = "{exp.month.not.blank}")
    private Integer expM;
    @NotNull(message = "{exp.year.not.blank}")
    private Integer expY;
    @Pattern(regexp = ValidationFormat.CVC_FORMAT, message = "{cvc.pattern}")
    private String cvc;
}
