package org.example.pricecalculatorservice.service.impl

import com.github.prominence.openweathermap.api.OpenWeatherMapClient
import com.github.prominence.openweathermap.api.enums.UnitSystem
import com.github.prominence.openweathermap.api.request.weather.CurrentWeatherRequester
import org.example.pricecalculatorservice.exception.SomethingWrongTemperatureServiceException
import org.example.pricecalculatorservice.service.TemperatureService
import org.springframework.stereotype.Service

@Service
class TemperatureServiceImpl(private val openweathermapClient: OpenWeatherMapClient) : TemperatureService {
    override fun getTemperatureByWeather(city: String): Double {


        try {

            val requester: CurrentWeatherRequester = openweathermapClient.currentWeather()
            val terminator = requester.single()
                .byCityName(city)
                .unitSystem(UnitSystem.METRIC)
                .retrieve()

            val temperature = terminator.asJava().temperature
            return temperature.value
        } catch (ex: Exception) {
            throw SomethingWrongTemperatureServiceException(ex.message.toString());
        }
    }
}