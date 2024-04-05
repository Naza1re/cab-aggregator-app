package org.example.carparkservice.service.impl

import org.example.carparkservice.dto.CarRequest
import org.example.carparkservice.dto.CarResponse
import org.example.carparkservice.exception.CarAlreadyExistException
import org.example.carparkservice.exception.CarNotFoundException
import org.example.carparkservice.mapper.CarMapper
import org.example.carparkservice.model.Car
import org.example.carparkservice.repository.CarParkRepository
import org.example.carparkservice.service.CarParkService
import org.example.carparkservice.util.ExceptionMessages
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.stereotype.Service

@Service
class CarParkServiceImp(
    private val carParkRepository: CarParkRepository,
    private val carMapper: CarMapper
) : CarParkService {
    override fun getCarById(id: Long): CarResponse {
        val car = getOrThrow(id)
        return carMapper.toCarResponse(car);
    }

    override fun createCar(car: CarRequest): CarResponse? {
        checkCarExists(car.number)
        val car = carMapper.toCar(car);
        return carMapper.toCarResponse(carParkRepository.save(car));
    }

    override fun deleteCarById(id: Long): CarResponse {
        val car = getOrThrow(id)
        carParkRepository.deleteById(id)
        return carMapper.toCarResponse(car);
    }

    private fun getOrThrow(id: Long): Car {
        return carParkRepository.findById(id)
            .orElseThrow { CarNotFoundException(ExceptionMessages.CAR_NOT_FOUND.format(id)) }
    }

    private fun checkCarExists(number: String) {
        if (carParkRepository.findCarByNumber(number).isPresent) {
            throw CarAlreadyExistException(ExceptionMessages.CAR_ALREADY_EXIST.format(number))
        }
    }


}