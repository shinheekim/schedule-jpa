package com.example.springjpa.schedule.api.dto;

import jakarta.validation.constraints.NotBlank;

public record ScheduleSaveRequest(
        Long userId,
        @NotBlank
        String title,
        @NotBlank
        String content
) {
}
