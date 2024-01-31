package com.example.driverservice.exception.error;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class AppError {
    private String message;
}
