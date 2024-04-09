package org.example.carparkservice.util

import lombok.experimental.UtilityClass
import org.example.carparkservice.dto.CarResponse
import org.example.carparkservice.model.Car

@UtilityClass
class CarTestUtil {

    companion object {
        private const val model: String = "testModel"
        private const val mark: String = "testMark"
        private const val year: Int = 1321
        private const val id: Long = 1321
        private const val owner: Long = 1
        private const val type: String = "testType"
        private const val color: String = "testColor"
        private const val number: String = "testNumber"

        fun getDefaultCarResponse(): CarResponse {
            return CarResponse(id, model, mark, year, type, color, number, owner)
        }

        fun getDefaultCar(): Car {
            return Car(id, model, mark, year, type, color, number, owner)
        }
    }
}