package com.djordjije11.libraryappapi.dto.book;

import com.djordjije11.libraryappapi.dto.author.AuthorShortDto;
import com.djordjije11.libraryappapi.dto.publisher.PublisherShortDto;
import java.util.List;

public record BookDto(
        long rowVersion,
        Long id,
        String title,
        String description,
        String imageUrl,
        Integer pagesNumber,
        PublisherShortDto publisher,
        List<AuthorShortDto> authors
) {
}
