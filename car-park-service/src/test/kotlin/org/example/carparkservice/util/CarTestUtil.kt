package org.example.carparkservice.util

import lombok.experimental.UtilityClass
import org.example.carparkservice.dto.CarRequest
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
        private const val model_update: String = "newModel"
        private const val mark_update: String = "newMark"
        private const val year_update: Int = 31213
        private const val type_update: String = "sport_type"
        private const val color_update: String = "newColor"
        private const val number_update: String = "newNumber"

        fun getNewCarResponse(): CarResponse {
            return CarResponse(
                id, model_update, mark_update, year_update, type_update, color_update, number_update,
                owner
            )
        }

        fun getNewCar(): Car {
            return Car(
                id,
                model_update,
                mark_update,
                year_update,
                type_update,
                color_update,
                number_update,
                owner
            )
        }

        fun getCarRequest(): CarRequest {
            return CarRequest(
                model_update, mark_update, year_update, type_update, color_update, number_update
            )
        }

        fun getDefaultCarResponse(): CarResponse {
            return CarResponse(id, model, mark, year, type, color, number, owner)
        }

        fun getDefaultCar(): Car {
            return Car(id, model, mark, year, type, color, number, owner)
        }

        fun getDefaultCarRequest(): CarRequest {
            return CarRequest(model, mark, year, type, color, number)
        }
    }
}