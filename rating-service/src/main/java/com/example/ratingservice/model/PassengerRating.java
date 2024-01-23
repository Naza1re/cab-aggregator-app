package com.example.ratingservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "passenger_rate")
public class PassengerRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "passenger_id")
    private Long passenger;
    private double rate;
}
