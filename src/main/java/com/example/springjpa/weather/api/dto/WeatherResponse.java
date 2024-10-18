package com.example.springjpa.weather.api.dto;

public record WeatherResponse(
        String date,
        String weather
) {
}
