package com.example.driverservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class DriverResponse {

    private Long id;
    private String name;
    private String surname;
    private String phone;
    private String email;
    private boolean available;
    private String number;
    private String color;
    private String model;

}
