package org.example.carparkservice.unit

import org.assertj.core.api.Assertions.assertThat
import org.example.carparkservice.mapper.CarMapper
import org.example.carparkservice.repository.CarParkRepository
import org.example.carparkservice.service.impl.CarParkServiceImp
import org.example.carparkservice.util.CarTestUtil
import org.junit.jupiter.api.Test
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


}