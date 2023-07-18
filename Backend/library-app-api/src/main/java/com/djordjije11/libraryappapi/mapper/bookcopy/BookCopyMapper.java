package com.djordjije11.libraryappapi.mapper.bookcopy;

import com.djordjije11.libraryappapi.dto.bookcopy.BookCopyCreateDto;
import com.djordjije11.libraryappapi.dto.bookcopy.BookCopyDto;
import com.djordjije11.libraryappapi.dto.bookcopy.BookCopyUpdateDto;
import com.djordjije11.libraryappapi.model.BookCopy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookCopyMapper {
    BookCopyDto map(BookCopy bookCopy);
    BookCopy map(BookCopyCreateDto bookCopyDto);
    BookCopy map(BookCopyUpdateDto bookCopyDto);
}
