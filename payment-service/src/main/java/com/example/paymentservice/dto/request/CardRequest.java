package com.example.paymentservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardRequest {
    @NotBlank(message = "{card.number.not.blank}")
    @Pattern(regexp = "\\d{16}", message = "{card.number.pattern}")
    private String cardNumber;
    @NotNull(message = "{exp.month.not.blank}")
    @Size(min = 1, max = 2, message = "{exp.month.size}")
    private Integer expM;
    @NotNull(message = "{exp.year.not.blank}")
    @Size(min = 4, max = 4, message = "{exp.year.size}")
    private Integer expY;
    @NotNull(message = "{cvc.not.blank}")
    @Pattern(regexp = "\\d{3}", message = "{cvc.pattern}")
    private Integer cvc;
}
