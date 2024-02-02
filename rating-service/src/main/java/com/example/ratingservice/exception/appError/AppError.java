package com.example.ratingservice.exception.appError;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class AppError {

    private String message;

}
