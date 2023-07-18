package com.djordjije11.libraryappapi.dto.lending;

import java.util.List;

public record LendingsReturnDto(
        List<Long> lendingsIds
) {
}
