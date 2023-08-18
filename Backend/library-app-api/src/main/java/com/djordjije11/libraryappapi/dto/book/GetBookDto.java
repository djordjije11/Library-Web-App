package com.djordjije11.libraryappapi.dto.book;

public record GetBookDto(
    BookDto book,
    Long availableCopiesInBuildingCount
) {
}
