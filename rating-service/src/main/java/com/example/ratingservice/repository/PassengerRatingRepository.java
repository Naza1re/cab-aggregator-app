package com.example.ratingservice.repository;

import com.example.ratingservice.model.PassengerRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PassengerRatingRepository extends JpaRepository<PassengerRating, Long> {
    Optional<PassengerRating> findPassengerRatingByPassenger(Long passenger);

    boolean existsByPassenger(Long passengerId);
}
