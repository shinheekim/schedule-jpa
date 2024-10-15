package com.example.springjpa.comment.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentSaveRequest(
        @NotNull(message = "userId는 null이 될 수 없습니다.")
        Long userId,
        @NotNull(message = "scheduleId는 null이 될 수 없습니다.")
        Long scheduleId,
        @NotBlank(message = "content는 blank가 될 수 없습니다.")
        String content
) {
}
