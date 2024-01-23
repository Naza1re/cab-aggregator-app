package com.example.ratingservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "driver_rate")
public class DriverRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "driver_id")
    private Long driver;
    private double rate;
}
