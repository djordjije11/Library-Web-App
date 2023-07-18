package com.djordjije11.libraryappapi.dto.bookcopy;

import com.djordjije11.libraryappapi.model.BookCopyStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record BookCopyCreateDto(
        @NotNull(message = "ISBN is mandatory.")
        @Pattern(regexp = "^\\d{3}-\\d{2}-\\d{4}-\\d{3}-\\d{1}$", message = "ISBN is not valid.")
        String isbn,
        BookCopyStatus status,
        Long bookId
) {
}
