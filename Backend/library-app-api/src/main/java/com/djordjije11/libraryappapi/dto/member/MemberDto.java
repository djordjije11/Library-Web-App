package com.djordjije11.libraryappapi.dto.member;

import com.djordjije11.libraryappapi.model.Gender;
import java.time.LocalDate;

public record MemberDto(
        long rowVersion,
        Long id,
        String idCardNumber,
        String firstname,
        String lastname,
        Gender gender,
        String email,
        LocalDate birthday
) {
}
