package com.example.driverservice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RatingRequest {

    @NotNull(message = "{id.not.null}")
    @Min(value = 0, message = "{rate.range}")
    private Long id;
}
