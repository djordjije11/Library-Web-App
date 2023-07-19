package com.djordjije11.libraryappapi.mapper.book;

import com.djordjije11.libraryappapi.dto.book.BookCreateDto;
import com.djordjije11.libraryappapi.dto.book.BookDto;
import com.djordjije11.libraryappapi.dto.book.BookShortDto;
import com.djordjije11.libraryappapi.dto.book.BookUpdateDto;
import com.djordjije11.libraryappapi.model.Author;
import com.djordjije11.libraryappapi.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookShortDto mapShort(Book book);
    @Mapping(source = "authorsIds", target = "authors")
    @Mapping(source = "publisherId", target = "publisher.id")
    Book map(BookCreateDto bookDto);
    default List<Author> map(List<Long> authorsIds){
        if(authorsIds == null){
            return null;
        }
        return authorsIds.stream().map(lId -> new Author(lId)).toList();
    }
    @Mapping(source = "authorsIds", target = "authors")
    @Mapping(source = "publisherId", target = "publisher.id")
    Book map(BookUpdateDto bookDto);
    BookDto map(Book book);
}
