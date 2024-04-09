package org.example.carparkservice.repository

import org.example.carparkservice.model.Car
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CarParkRepository : JpaRepository<Car,Long> {

    fun findCarByNumber(number: String): Optional<Car>
    fun existsByNumber(number: String): Boolean
}