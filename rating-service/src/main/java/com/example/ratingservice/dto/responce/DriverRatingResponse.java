package com.example.ratingservice.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DriverRatingResponse {
    private Long id;
    private Long driver;
    private double rate;

}
