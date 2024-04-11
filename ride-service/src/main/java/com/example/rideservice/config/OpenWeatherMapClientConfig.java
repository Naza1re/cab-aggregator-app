package com.example.rideservice.config;

import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenWeatherMapClientConfig {


    @Value("${openweather.key}")
    private String KEY;

    @Bean
    public OpenWeatherMapClient openWeatherMapClient() {
        return new OpenWeatherMapClient(KEY);
    }
}
