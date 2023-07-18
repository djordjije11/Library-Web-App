package com.djordjije11.libraryappapi.dto.bookcopy;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record BookCopyUpdateDto(
        long rowVersion,
        @NotNull(message = "ISBN is mandatory.")
        @Pattern(regexp = "^\\d{3}-\\d{2}-\\d{4}-\\d{3}-\\d{1}$", message = "ISBN is not valid.")
        String isbn
) {
}
