package com.example.driverservice.dto.request;

import com.example.driverservice.util.ValidationFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@Builder
public class DriverRequest {
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

    @NotBlank(message = "{number.not.blank}")
    private String number;

    @NotBlank(message = "{color.not.blank}")
    private String color;

    @NotBlank(message = "{model.not.blank}")
    private String model;
}
