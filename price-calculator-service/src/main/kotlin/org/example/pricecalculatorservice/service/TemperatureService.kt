package org.example.pricecalculatorservice.service

interface TemperatureService {


    fun reducePriceByWeather(city: String): Double


}