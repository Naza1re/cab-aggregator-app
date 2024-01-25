package com.example.ratingservice.repository;

import com.example.ratingservice.model.DriverRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRatingRepository extends JpaRepository<DriverRating, Long> {
    Optional<DriverRating> findDriverRatingByDriver(Long driver);

    boolean existsByDriver(Long driverId);
}
