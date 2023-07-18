package com.djordjije11.libraryappapi.dto.book;

import com.djordjije11.libraryappapi.dto.author.AuthorShortDto;
import java.util.List;

public record BookShortDto(
        Long id,
        String title,
        List<AuthorShortDto> authors
) {
}
