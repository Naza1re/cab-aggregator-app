package org.example.pricecalculatorservice.service

import java.math.BigDecimal

interface TemperatureService {


    fun getTemperatureByWeather(city: String): Double


}