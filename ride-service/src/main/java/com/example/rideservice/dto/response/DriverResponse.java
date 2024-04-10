package com.example.rideservice.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
