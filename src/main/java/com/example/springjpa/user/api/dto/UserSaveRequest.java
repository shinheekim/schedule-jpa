package com.example.springjpa.user.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserSaveRequest(
        @Size(max = 5)
        @NotBlank
        String name,
        @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
        @NotBlank
        String email
) {
}
