package com.example.passengerservice.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@EqualsAndHashCode
public class PassengerResponse {
    private Long id;
    private String name;
    private String surname;
    private String phone;
    private String email;
}
