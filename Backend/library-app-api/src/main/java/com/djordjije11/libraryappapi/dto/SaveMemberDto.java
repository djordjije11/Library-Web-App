package com.djordjije11.libraryappapi.dto;

import com.djordjije11.libraryappapi.model.Gender;
import com.djordjije11.libraryappapi.validation.Before;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record SaveMemberDto(
        @Size(min = 10, max = 20, message = "IdCardNumber must be between 10 and 20 characters.")
        @Pattern(regexp = "^\\d{10,20}$", message = "IdCardNumber must be valid.")
        String idCardNumber,
        @NotBlank(message = "Firstname is mandatory.")
        String firstname,
        @NotBlank(message = "Lastname is mandatory.")
        String lastname,
        Gender gender,
        @NotBlank
        @Email(message = "Email must be valid.")
        String email,
        @Past(message = "Birthday must be in past.")
        @Before(years = 16, message = "Member must be at least 16 years old.")
        LocalDate birthday
) {
}
