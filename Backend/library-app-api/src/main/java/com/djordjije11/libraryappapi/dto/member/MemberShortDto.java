package com.djordjije11.libraryappapi.dto.member;

public record MemberShortDto(
        Long id,
        String idCardNumber,
        String firstname,
        String lastname,
        String email
) {
}
