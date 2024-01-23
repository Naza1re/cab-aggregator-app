package com.example.ratingservice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRequest {

    @NotNull(message = "{id.not.null}")
    @Min(value = 0, message = "{rate.range}")
    private Long id;
}
