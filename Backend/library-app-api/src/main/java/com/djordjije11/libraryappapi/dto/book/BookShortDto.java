package com.djordjije11.libraryappapi.dto.book;

public record BookShortDto(
        Long id,
        String title,
        String publisher,
        String authors
) {
}
