package com.example.springjpa.user.api.dto.response;

import com.example.springjpa.user.domain.User;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserResponse(
        String name,
        String nickname,
        String email,
        LocalDateTime createAt,
        LocalDateTime modifiedAt
) {
    public static UserResponse from(User user) {
        return UserResponse.builder()
                .name(user.getName())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .createAt(user.getCreatedAt())
                .modifiedAt(user.getModifiedAt())
                .build();
    }
}
