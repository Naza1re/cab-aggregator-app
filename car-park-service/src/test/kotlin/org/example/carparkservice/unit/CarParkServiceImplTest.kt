package org.example.carparkservice.unit

import org.assertj.core.api.Assertions.assertThat
import org.example.carparkservice.exception.CarAlreadyExistException
import org.example.carparkservice.exception.CarNotFoundException
import org.example.carparkservice.mapper.CarMapper
import org.example.carparkservice.model.Car
import org.example.carparkservice.repository.CarParkRepository
import org.example.carparkservice.service.impl.CarParkServiceImp
import org.example.carparkservice.util.CarTestUtil
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*


@ExtendWith(MockitoExtension::class)
class CarParkServiceImplTest {
    @Mock
    private lateinit var carParkRepository: CarParkRepository

    @InjectMocks
    private lateinit var service: CarParkServiceImp

    @Mock
    private lateinit var carMapper: CarMapper


    @Test
    @DisplayName("Getting existing car")
    fun getCarById_whenCariExist() {
        doReturn(CarTestUtil.getDefaultCarResponse()).`when`(carMapper).toCarResponse(CarTestUtil.getDefaultCar())
        doReturn(Optional.of(CarTestUtil.getDefaultCar())).`when`(carParkRepository).findById(anyLong())

        val actual = service.getCarById(id = 1L)

        assertThat(actual).isEqualTo(CarTestUtil.getDefaultCarResponse())
    }

    @DisplayName("Getting not existing car")
    @Test
    fun getCarById_whenCariNotExist() {
        doReturn(Optional.empty<Car>()).`when`(carParkRepository).findById(anyLong())

        assertThrows<CarNotFoundException> {
            service.getCarById(id = 100L)
        }
    }

    @DisplayName("Creating car with same number")
    @Test
    fun createCarById_whenCariExist() {
        doReturn(true).`when`(carParkRepository).existsByNumber(CarTestUtil.getDefaultCar().number)

        assertThrows<CarAlreadyExistException> {
            service.createCar(CarTestUtil.getDefaultCarRequest())
        }
    }

    @DisplayName("Updating existing car")
    @Test
    fun updateCar_whenCariExist() {
        doReturn(Optional.of(CarTestUtil.getDefaultCar())).`when`(carParkRepository).findById(anyLong())
        doReturn(CarTestUtil.getNewCar()).`when`(carMapper).toCar(CarTestUtil.getCarRequest())
        doReturn(CarTestUtil.getNewCar()).`when`(carParkRepository).save(CarTestUtil.getNewCar())
        doReturn(CarTestUtil.getNewCarResponse()).`when`(carMapper).toCarResponse(CarTestUtil.getNewCar())

        val actual = service.updateCarById(1L, CarTestUtil.getCarRequest())

        assertThat(actual).isEqualTo(CarTestUtil.getNewCarResponse())
    }

    @DisplayName("Deleting not existing car")
    @Test
    fun deleteCarById_whenCariNotExist() {
        doReturn(Optional.empty<Car>()).`when`(carParkRepository).findById(anyLong())

        assertThrows<CarNotFoundException> {
            service.deleteCarById(id = 100L)
        }
    }


}