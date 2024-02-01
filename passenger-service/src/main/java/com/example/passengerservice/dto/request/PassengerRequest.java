package com.example.passengerservice.dto.request;

import com.example.passengerservice.util.ValidationFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Setter
@Builder
@Getter
public class PassengerRequest {

    @NotBlank(message = "{name.not.blanked}")
    @Size(max = 16, message = "{name.max.value}")
    private String name;

    @NotBlank(message = "{surname.not.blanked}")
    @Size(max = 16, message = "{surname.max.value}")
    private String surname;

    @NotBlank(message = "{phone.not.blanked}")
    @Pattern(regexp = ValidationFormat.PHONE_REGEX, message = "{phone.invalid.message}")
    private String phone;

    @NotBlank(message = "{email.not.blanked}")
    @Pattern(regexp = ValidationFormat.EMAIL_REGEX, message = "{email.invalid.message}")
    private String email;

}
