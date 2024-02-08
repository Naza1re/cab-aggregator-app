package com.example.ratingservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@Table(name = "driver_rate")
public class DriverRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "driver_id")
    private Long driver;
    private double rate;
}
