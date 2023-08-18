package com.djordjije11.libraryappapi.mapper.bookcopy;

import com.djordjije11.libraryappapi.dto.bookcopy.BookCopyCreateDto;
import com.djordjije11.libraryappapi.dto.bookcopy.BookCopyDto;
import com.djordjije11.libraryappapi.dto.bookcopy.BookCopyUpdateDto;
import com.djordjije11.libraryappapi.mapper.HelperMapper;
import com.djordjije11.libraryappapi.model.BookCopy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookCopyMapper extends HelperMapper {
    @Mapping(source = "book.publisher.name", target = "book.publisher")
    @Mapping(source = "book.authors", target = "book.authors", qualifiedByName = MAP_AUTHORS_TO_STRING)
    @Mapping(source = "building", target = "building", qualifiedByName = MAP_BUILDING_TO_STRING)
    BookCopyDto map(BookCopy bookCopy);

    BookCopy map(BookCopyCreateDto bookCopyDto);

    BookCopy map(BookCopyUpdateDto bookCopyDto);
}
