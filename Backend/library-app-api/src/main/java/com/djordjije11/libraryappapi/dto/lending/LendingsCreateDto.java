package com.djordjije11.libraryappapi.dto.lending;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record LendingsCreateDto(
        @NotNull(message = "MemberId is mandatory.")
        Long memberId,
        @NotNull(message = "BookCopiesIds are mandatory.")
        List<Long> bookCopiesIds
) {
}
