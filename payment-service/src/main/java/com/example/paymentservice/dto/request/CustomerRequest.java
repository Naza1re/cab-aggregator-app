package com.example.paymentservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
    @NotBlank(message = "{name.not.blank}")
    @Size(min = 2, max = 50, message = "{name.size}")
    private String name;
    @NotBlank(message = "{email.not.blank}")
    @Email(message = "{email.invalid}")
    private String email;
    @NotBlank(message = "{phone.not.blank}")
    @Size(min = 10, max = 15, message = "{phone.size}")
    private String phone;
    @NotNull(message = "{passenger.id.not.blank}")
    private Long passengerId;
    @NotNull(message = "{amount.not.blank}")
    private Long amount;
}
