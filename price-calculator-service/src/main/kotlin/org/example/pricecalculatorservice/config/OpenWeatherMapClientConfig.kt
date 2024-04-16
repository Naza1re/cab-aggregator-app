package com.example.rideservice.config

import com.github.prominence.openweathermap.api.OpenWeatherMapClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenWeatherMapClientConfig {

    @Value("\${openweather.key}")
    private lateinit var key: String

    @Bean
    fun openWeatherMapClient(): OpenWeatherMapClient {
        return OpenWeatherMapClient(key)
    }
}
