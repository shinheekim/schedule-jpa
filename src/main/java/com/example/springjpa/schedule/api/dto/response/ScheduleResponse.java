package com.example.springjpa.schedule.api.dto.response;

import com.example.springjpa.schedule.domain.Schedule;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ScheduleResponse(
        Long id,
        String title,
        String content,
        int commentNums,
        LocalDateTime createAt,
        LocalDateTime modifiedAt,
        String creator
) {
    public static ScheduleResponse from(Schedule schedule) {
        return ScheduleResponse.builder()
                .id(schedule.getId())
                .title(schedule.getTitle())
                .content(schedule.getContent())
                .commentNums(schedule.getComments().size())
                .createAt(schedule.getCreatedAt())
                .modifiedAt(schedule.getModifiedAt())
                .creator(schedule.getCreator().getName())
                .build();
    }
}
