package com.example.springjpa.schedule.api.dto;

import com.example.springjpa.schedule.domain.Schedule;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ScheduleResponse(
        Long id,
        String title,
        String content,
        LocalDateTime createAt,
        LocalDateTime updateAt
) {
    public static ScheduleResponse from(Schedule schedule) {
        return ScheduleResponse.builder()
                .title(schedule.getTitle())
                .content(schedule.getContent())
                .build();
    }
}
