package org.example.carparkservice.service.impl

import org.example.carparkservice.dto.CarListResponse
import org.example.carparkservice.dto.CarOwnerRequest
import org.example.carparkservice.dto.CarRequest
import org.example.carparkservice.dto.CarResponse
import org.example.carparkservice.exception.CarAlreadyExistException
import org.example.carparkservice.exception.CarNotFoundException
import org.example.carparkservice.mapper.CarMapper
import org.example.carparkservice.model.Car
import org.example.carparkservice.repository.CarParkRepository
import org.example.carparkservice.service.CarParkService
import org.example.carparkservice.util.ExceptionMessages
import org.example.pricecalculatorservice.security.SecurityConstants.EMAIL
import org.example.pricecalculatorservice.security.SecurityConstants.FAMILY_NAME
import org.example.pricecalculatorservice.security.SecurityConstants.GIVEN_NAME
import org.example.pricecalculatorservice.security.SecurityConstants.ID
import org.example.pricecalculatorservice.security.SecurityConstants.PHONE
import org.example.pricecalculatorservice.security.SecurityConstants.USERNAME
import org.example.pricecalculatorservice.security.User
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Service
import java.util.*

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


    override fun findAllCars(): CarListResponse? {
        val cars = carParkRepository.findAll()
        val carResponseList: List<CarResponse> = cars.map { car -> carMapper.toCarResponse(car) }
        return CarListResponse(carResponseList)
    }

    override fun setDriverToCar(carOwnerRequest: CarOwnerRequest): CarResponse? {
        val car = getOrThrow(carOwnerRequest.carId)
        car.owner = carOwnerRequest.owner
        return carMapper.toCarResponse(carParkRepository.save(car))
    }

    override fun extractUserInfo(jwt: Jwt): User {
        return User(
            phone = jwt.getClaimAsString(PHONE),
            surname = jwt.getClaimAsString(FAMILY_NAME),
            name1 = jwt.getClaimAsString(GIVEN_NAME),
            id = UUID.fromString(jwt.getClaimAsString(ID)),
            email = jwt.getClaimAsString(EMAIL),
            username1 = jwt.getClaimAsString(USERNAME)
        )
    }

    override fun updateCarById(id: Long, carRequest: CarRequest): CarResponse? {
        val car = getOrThrow(id)
        val carToSave = carMapper.toCar(carRequest)
        carToSave.id = car.id
        carToSave.owner = car.owner
        return carMapper.toCarResponse(carParkRepository.save(carToSave))
    }

    private fun getOrThrow(id: Long): Car {
        return carParkRepository.findById(id)
            .orElseThrow { CarNotFoundException(ExceptionMessages.CAR_NOT_FOUND.format(id)) }
    }

    private fun checkCarExists(number: String) {
        if (carParkRepository.existsByNumber(number)) {
            throw CarAlreadyExistException(ExceptionMessages.CAR_ALREADY_EXIST.format(number))
        }
    }


}