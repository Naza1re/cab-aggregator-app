package com.example.rideservice.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PromoCodeResponse {
    private Long id;
    private String name;
    private String value;
    private double percent;

}
