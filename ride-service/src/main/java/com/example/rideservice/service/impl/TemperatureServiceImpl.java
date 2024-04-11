package com.example.rideservice.service.impl;

import com.example.rideservice.service.TemperatureService;
import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import com.github.prominence.openweathermap.api.model.Temperature;
import com.github.prominence.openweathermap.api.request.weather.CurrentWeatherRequester;
import com.github.prominence.openweathermap.api.request.weather.single.SingleResultCurrentWeatherRequestTerminator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TemperatureServiceImpl implements TemperatureService {

    private final OpenWeatherMapClient client;

    @Override
    public double calculatePrice(String city) {
        CurrentWeatherRequester requester = client.currentWeather();

        SingleResultCurrentWeatherRequestTerminator terminator = requester.single()
                .byCityName(city)
                .unitSystem(UnitSystem.METRIC)
                .retrieve();

        Temperature temperature = terminator.asJava().getTemperature();
        return temperature.getValue();
    }
}
