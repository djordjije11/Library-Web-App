package com.djordjije11.libraryappapi.dto.lending;

import java.util.List;

public record LendingsCreateDto(
        Long memberId,
        List<Long> bookCopiesIds
) {
}
