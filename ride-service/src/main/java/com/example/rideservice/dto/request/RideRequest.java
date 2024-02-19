package com.example.rideservice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Builder;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RideRequest {
    private Long id;

    @NotNull(message = "${passenger.id.null}")
    @Min(value = 0, message = "${passenger.id.value}")
    private Long passengerId;

    @NotBlank(message = "${pickup.address.blank}")
    private String pickUpAddress;

    @NotBlank(message = "${drop.off.address.blank}")
    private String dropOffAddress;

    @Size(max = 50, message = "${instructions.max.length}")
    private String instructions;

}
