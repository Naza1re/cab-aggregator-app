package org.example.carparkservice.service

import org.example.carparkservice.dto.CarListResponse
import org.example.carparkservice.dto.CarOwnerRequest
import org.example.carparkservice.dto.CarRequest
import org.example.carparkservice.dto.CarResponse
import org.springframework.security.oauth2.jwt.Jwt


interface CarParkService {
    fun getCarById(id: Long): CarResponse
    fun createCar(car: CarRequest): CarResponse?
    fun deleteCarById(id: Long): CarResponse?
    fun findAllCars(): CarListResponse?
    fun setDriverToCar(carOwnerRequest: CarOwnerRequest): CarResponse?
    fun extractUserInfo(jwt: Jwt): Any?


}