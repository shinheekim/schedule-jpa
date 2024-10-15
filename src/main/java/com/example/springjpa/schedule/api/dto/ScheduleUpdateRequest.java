package com.example.springjpa.schedule.api.dto;

import jakarta.validation.constraints.Size;

public record ScheduleUpdateRequest(
        @Size(max = 10)
        String title,
        String content
) {
}
