package com.example.paymentservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PassengerResponse {
    private Long id;
    private String name;
    private String surname;
    private String phone;
    private String email;
}
