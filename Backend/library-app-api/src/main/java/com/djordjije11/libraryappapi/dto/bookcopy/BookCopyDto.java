package com.djordjije11.libraryappapi.dto.bookcopy;

import com.djordjije11.libraryappapi.dto.book.BookShortDto;
import com.djordjije11.libraryappapi.model.BookCopyStatus;

public record BookCopyDto(
        long rowVersion,
        Long id,
        String isbn,
        BookCopyStatus status,
        BookShortDto book
) {
}
