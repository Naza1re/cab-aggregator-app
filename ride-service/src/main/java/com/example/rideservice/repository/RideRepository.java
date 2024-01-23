package com.example.rideservice.repository;

import com.example.rideservice.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {
    List<Ride> getAllByPassengerId(Long passengerId);

    List<Ride> getAllByDriverId(Long driverId);

}
