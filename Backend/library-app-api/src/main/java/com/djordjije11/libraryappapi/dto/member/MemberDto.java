package com.djordjije11.libraryappapi.dto.member;

import com.djordjije11.libraryappapi.model.Gender;
import com.djordjije11.libraryappapi.validation.before.Before;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record MemberDto(
        long rowVersion,
        Long id,
        @Size(min = 10, max = 20, message = "IdCardNumber must be between 10 and 20 characters.")
        @Pattern(regexp = "^\\d{10,20}$", message = "IdCardNumber must be valid.")
        String idCardNumber,
        @NotBlank(message = "Firstname is mandatory.")
        String firstname,
        @NotBlank(message = "Lastname is mandatory.")
        String lastname,
        Gender gender,
        @NotBlank(message = "Email is mandatory.")
        @Email(message = "Email must be valid.")
        String email,
        @Before(years = 16, message = "Member must be at least 16 years old.")
        LocalDate birthday
) {
}
