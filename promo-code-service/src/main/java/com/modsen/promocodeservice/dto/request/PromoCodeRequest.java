package com.modsen.promocodeservice.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

@Getter
@Setter
@Builder

public class PromoCodeRequest {
    @NotBlank(message = "{promo.code.name.not.blanked}")
    @Size(max = 16, message = "{name.max.value}")
    private String name;
    @NotBlank(message = "{value.not.blanked}")
    @Size(max = 5, message = "{value.max.value}")
    private String value;
    @Max(value = 50 , message = "{percent.max.value}")
    private double percent;
}
