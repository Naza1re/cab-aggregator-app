package com.example.driverservice.repository;

import com.example.driverservice.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    List<Driver> getAllByAvailable(boolean available);

    boolean existsByNumber(String number);

    boolean existsByPhone(String phone);

    boolean existsByEmail(String email);

}
