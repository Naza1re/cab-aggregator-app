package com.example.ratingservice.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Setter
@Builder
public class UpdateRequest {

    @NotNull(message = "{id.not.null}")
    @Min(value = 0, message = "{id.not.negative}")
    private Long id;

    @NotNull(message = "{rate.not.null}")
    @DecimalMin(value = "1.0", inclusive = true, message = "{rate.range}")
    @DecimalMax(value = "5.0", inclusive = true, message = "{rate.range}")
    private double rate;

}
