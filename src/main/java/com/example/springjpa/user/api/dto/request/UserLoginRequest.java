package com.example.springjpa.user.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserLoginRequest(
        @NotBlank
        @Email
        String email,
        @NotBlank
        String password
) {
}
