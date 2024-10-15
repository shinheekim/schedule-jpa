package com.example.springjpa.user.api.dto;

import com.example.springjpa.user.domain.User;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserResponse(
        String name,
        String email,
        LocalDateTime createAt,
        LocalDateTime modifiedAt
) {
    public static UserResponse from(User user) {
        return UserResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .createAt(user.getCreatedAt())
                .modifiedAt(user.getModifiedAt())
                .build();
    }
}
