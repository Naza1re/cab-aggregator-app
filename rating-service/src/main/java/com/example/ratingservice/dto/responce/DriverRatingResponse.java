package com.example.ratingservice.dto.responce;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class DriverRatingResponse {
    private Long id;
    private Long driver;
    private double rate;

}
