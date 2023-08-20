package com.djordjije11.libraryappapi.mapper.book;

import com.djordjije11.libraryappapi.dto.book.BookCreateDto;
import com.djordjije11.libraryappapi.dto.book.BookDto;
import com.djordjije11.libraryappapi.dto.book.BookShortDto;
import com.djordjije11.libraryappapi.dto.book.BookUpdateDto;
import com.djordjije11.libraryappapi.mapper.HelperMapper;
import com.djordjije11.libraryappapi.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper extends HelperMapper {
    @Mapping(source = "publisher.name", target = "publisher")
    @Mapping(source = "authors", target = "authors", qualifiedByName = MAP_AUTHORS_TO_STRING)
    BookShortDto mapShort(Book book);

    @Mapping(source = "authorsIds", target = "authors", qualifiedByName = MAP_IDS_TO_AUTHORS)
    @Mapping(source = "publisherId", target = "publisher", qualifiedByName = MAP_PUBLISHER_ID_TO_PUBLISHER)
    Book map(BookCreateDto bookDto);

    @Mapping(source = "authorsIds", target = "authors", qualifiedByName = MAP_IDS_TO_AUTHORS)
    @Mapping(source = "publisherId", target = "publisher", qualifiedByName = MAP_PUBLISHER_ID_TO_PUBLISHER)
    Book map(BookUpdateDto bookDto);

    BookDto map(Book book);
}
