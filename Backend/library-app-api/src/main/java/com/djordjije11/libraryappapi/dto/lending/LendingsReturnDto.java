package com.djordjije11.libraryappapi.dto.lending;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record LendingsReturnDto(
        @NotNull(message = "LendingsIds are mandatory.")
        List<Long> lendingsIds,
        @NotNull(message = "MemberId is mandatory.")
        Long memberId
) {
}
