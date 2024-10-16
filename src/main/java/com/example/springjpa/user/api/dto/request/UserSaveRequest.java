package com.example.springjpa.user.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserSaveRequest(
        @Size(max = 5)
        @NotBlank
        String name,
        String nickname,
        @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "이메일 형식이 아닙니다.")
        @NotBlank
        String email,
        @NotBlank
        String password
) {
}
