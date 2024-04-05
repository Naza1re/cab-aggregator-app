package org.example.carparkservice.repository

import org.example.carparkservice.model.Car
import org.springframework.data.jpa.repository.JpaRepository

interface CarParkRepository : JpaRepository<Car,Long> {
}