package org.example.carparkservice.mapper

import org.example.carparkservice.dto.CarRequest
import org.example.carparkservice.dto.CarResponse
import org.example.carparkservice.model.Car
import org.springframework.stereotype.Component

@Component
class CarMapperImpl : CarMapper {

    override fun toCar(carRequest: CarRequest): Car {
        return Car(
            id = 0L,
            model = carRequest.model,
            mark = carRequest.mark,
            year = carRequest.year,
            type = carRequest.type,
            color = carRequest.color,
            number = carRequest.number
        )
    }

    override fun toCarResponse(car: Car): CarResponse {
        return CarResponse(
            id = car.id,
            model = car.model,
            mark = car.mark,
            year = car.year,
            type = car.type,
            color = car.color,
            number = car.number
        )
    }
}