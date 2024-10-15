package com.example.springjpa.schedule.api.dto.response;

import com.example.springjpa.schedule.domain.Schedule;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ScheduleResponse(
        Long id,
        String title,
        String content,
        LocalDateTime createAt,
        LocalDateTime modifiedAt
) {
    public static ScheduleResponse from(Schedule schedule) {
        return ScheduleResponse.builder()
                .id(schedule.getId())
                .title(schedule.getTitle())
                .content(schedule.getContent())
                .createAt(schedule.getCreatedAt())
                .modifiedAt(schedule.getModifiedAt())
                .build();
    }
}
