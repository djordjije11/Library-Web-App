package com.djordjije11.libraryappapi.dto.book;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record BookUpdateDto(
        long rowVersion,
        @NotNull(message = "Title is mandatory.")
        String title,
        String description,
        String imageUrl,
        Integer pagesNumber,
        Long publisherId,
        List<Long> authorsIds
) {
}
