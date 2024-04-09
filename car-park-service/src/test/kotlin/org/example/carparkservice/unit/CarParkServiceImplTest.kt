package org.example.carparkservice.unit

import jakarta.ws.rs.NotFoundException
import org.assertj.core.api.Assertions.assertThat
import org.example.carparkservice.exception.CarAlreadyExistException
import org.example.carparkservice.exception.CarNotFoundException
import org.example.carparkservice.mapper.CarMapper
import org.example.carparkservice.model.Car
import org.example.carparkservice.repository.CarParkRepository
import org.example.carparkservice.service.impl.CarParkServiceImp
import org.example.carparkservice.util.CarTestUtil
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.junit.jupiter.MockitoExtension
import java.util.Optional


@ExtendWith(MockitoExtension::class)
class CarParkServiceImplTest {
    @Mock
    private lateinit var carParkRepository: CarParkRepository

    @InjectMocks
    private lateinit var service: CarParkServiceImp

    @Mock
    private lateinit var carMapper: CarMapper


    @Test
    fun getCarByIdWhenCariExist() {
        doReturn(CarTestUtil.getDefaultCarResponse()).`when`(carMapper).toCarResponse(CarTestUtil.getDefaultCar())
        doReturn(Optional.of(CarTestUtil.getDefaultCar())).`when`(carParkRepository).findById(anyLong())

        var actual = service.getCarById(id = 1L)

        assertThat(actual).isEqualTo(CarTestUtil.getDefaultCarResponse())
    }

    @Test
    fun getCarByIdWhenCariNotExist() {
        doReturn(Optional.empty<Car>()).`when`(carParkRepository).findById(anyLong())

        assertThrows<CarNotFoundException> {
            service.getCarById(id = 100L)
        }
    }

    @Test
    fun createCarByIdWhenCariExist() {
        doReturn(true).`when`(carParkRepository).existsByNumber(CarTestUtil.getDefaultCar().number)

        assertThrows<CarAlreadyExistException> {
            service.createCar(CarTestUtil.getDefaultCarRequest())
        }
    }


}