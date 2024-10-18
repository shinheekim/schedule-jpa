package com.example.springjpa.weather.application;

import com.example.springjpa.schedule.domain.Schedule;
import com.example.springjpa.weather.api.dto.WeatherResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final WebClient webClient;
    private static final String WEATHER_API_URL = "https://f-api.github.io/f-api/weather.json";

    public String getWeatherForCreatedAt(LocalDateTime createdAt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        String formattedDate = createdAt.format(formatter);

        return getWeatherForDate(formattedDate);
    }
    public String getWeatherForDate(String date) {
        WeatherResponse[] responses = webClient.get()
                .uri(WEATHER_API_URL)
                .retrieve()
                .bodyToMono(WeatherResponse[].class)
                .block();

        return Optional.ofNullable(responses)
                .stream()
                .flatMap(Arrays::stream)
                .filter(response -> response.date().equals(date))
                .map(WeatherResponse::weather)
                .findFirst()
                .orElse("Unknown");
    }
}
