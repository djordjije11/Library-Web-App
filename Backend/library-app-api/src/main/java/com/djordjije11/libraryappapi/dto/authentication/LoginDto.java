package com.djordjije11.libraryappapi.dto.authentication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginDto(
        @NotBlank
        @Size(min = 3, max = 30)
        String username,
        @NotBlank
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$",
                message = "Password must have minimum 8 characters, at least one uppercase letter, one lowercase letter and one number."
        )
        String password
) {
}
