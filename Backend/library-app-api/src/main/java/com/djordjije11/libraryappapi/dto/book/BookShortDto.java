package com.djordjije11.libraryappapi.dto.book;

import com.djordjije11.libraryappapi.dto.author.AuthorShortDto;
import com.djordjije11.libraryappapi.dto.publisher.PublisherShortDto;
import java.util.List;

public record BookShortDto(
        Long id,
        String title,
        PublisherShortDto publisher,
        List<AuthorShortDto> authors
) {
}
