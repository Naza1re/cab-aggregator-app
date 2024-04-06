package org.example.carparkservice.service

import org.example.carparkservice.dto.CarListResponse
import org.example.carparkservice.dto.CarRequest
import org.example.carparkservice.dto.CarResponse


interface CarParkService {
    fun getCarById(id: Long): CarResponse
    fun createCar(car: CarRequest): CarResponse?
    fun deleteCarById(id: Long): CarResponse?
    fun findAllCars(): CarListResponse?


}