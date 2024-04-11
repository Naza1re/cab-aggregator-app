package org.example.carparkservice.mapper

import org.example.carparkservice.dto.CarRequest
import org.example.carparkservice.dto.CarResponse
import org.example.carparkservice.model.Car

interface CarMapper {

    fun toCar(carRequest: CarRequest): Car

    fun toCarResponse(car: Car): CarResponse
}