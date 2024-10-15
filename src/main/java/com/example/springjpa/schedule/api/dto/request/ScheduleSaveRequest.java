package com.example.springjpa.schedule.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ScheduleSaveRequest(
        Long userId,
        @NotBlank
        @Size(max = 10)
        String title,
        @NotBlank
        String content
) {
}
