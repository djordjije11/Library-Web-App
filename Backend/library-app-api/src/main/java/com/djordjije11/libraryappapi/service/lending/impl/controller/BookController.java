package com.djordjije11.libraryappapi.service.lending.impl.controller;

import com.djordjije11.libraryappapi.dto.RowVersionDto;
import com.djordjije11.libraryappapi.dto.book.*;
import com.djordjije11.libraryappapi.dto.bookcopy.BookCopyCreateDto;
import com.djordjije11.libraryappapi.dto.bookcopy.BookCopyDto;
import com.djordjije11.libraryappapi.dto.bookcopy.BookCopyUpdateDto;
import com.djordjije11.libraryappapi.exception.book.BookCopyIsbnNotUniqueException;
import com.djordjije11.libraryappapi.exception.book.BookCopyNotInBuildingException;
import com.djordjije11.libraryappapi.exception.book.BookWithCopiesDeleteException;
import com.djordjije11.libraryappapi.mapper.book.BookMapper;
import com.djordjije11.libraryappapi.mapper.bookcopy.BookCopyMapper;
import com.djordjije11.libraryappapi.model.Book;
import com.djordjije11.libraryappapi.model.BookCopy;
import com.djordjije11.libraryappapi.model.Building;
import com.djordjije11.libraryappapi.service.book.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {
    private final BookService bookService;
    private final BookMapper bookMapper;
    private final BookCopyMapper bookCopyMapper;

    public BookController(BookService bookService, BookMapper bookMapper, BookCopyMapper bookCopyMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
        this.bookCopyMapper = bookCopyMapper;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BookShortDto>> getAll() {
        return ResponseEntity.ok(bookService.getAll().stream().map(bookMapper::mapShort).toList());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GetBookDto> get(@PathVariable Long id){
        BookDto bookDto = bookMapper.map(bookService.get(id));
        Long availableBookCopiesCount = bookService.getAvailableBookCopiesCount(id);
        return ResponseEntity.ok(new GetBookDto(bookDto, availableBookCopiesCount));
    }

    @GetMapping("/{bookId}/book-copy")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BookCopyDto>> getAllCopies(@PathVariable Long bookId) {
        // TODO: 7/19/2023 getBuilding().getId();
        Long buildingId = 1L;
        return ResponseEntity.ok(bookService.getAllCopies(bookId, buildingId).stream().map(bookCopyMapper::map).toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BookDto> createBook(@Valid @RequestBody BookCreateDto bookCreateDto) {
        Book book = bookMapper.map(bookCreateDto);
        BookDto bookDto = bookMapper.map(bookService.create(book));
        return new ResponseEntity<>(bookDto, HttpStatus.CREATED);
    }

    @PostMapping("/{bookId}/book-copy")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BookCopyDto> createBookCopy(
            @PathVariable Long bookId,
            @Valid @RequestBody BookCopyCreateDto bookCopyCreateDto
    ) throws BookCopyIsbnNotUniqueException {
        BookCopy bookCopy = bookCopyMapper.map(bookCopyCreateDto);
        bookCopy.setBook(new Book(bookId));
        // TODO: 7/18/2023 bookCopy.setBuilding(...);
        return new ResponseEntity<>(bookCopyMapper.map(bookService.createCopy(bookCopy)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BookDto> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookUpdateDto bookUpdateDto
    ) {
        Book book = bookMapper.map(bookUpdateDto);
        book.setId(id);
        BookDto bookDto = bookMapper.map(bookService.update(book));
        return ResponseEntity.ok(bookDto);
    }

    @PutMapping("/{bookId}/book-copy/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BookCopyDto> updateBookCopy(
            @PathVariable Long bookId,
            @PathVariable Long id,
            @Valid @RequestBody BookCopyUpdateDto bookCopyUpdateDto
    ) throws BookCopyIsbnNotUniqueException, BookCopyNotInBuildingException {
        BookCopy bookCopy = bookCopyMapper.map(bookCopyUpdateDto);
        bookCopy.setBook(new Book(bookId));
        bookCopy.setId(id);
        // TODO: 7/19/2023 bookCopy.setBuilding(...)
        return ResponseEntity.ok(bookCopyMapper.map(bookService.updateCopy(bookCopy)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deleteBook(@PathVariable Long id) throws BookWithCopiesDeleteException {
        bookService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{bookId}/book-copy/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> discardBookCopy(
            @PathVariable Long id,
            @RequestBody RowVersionDto rowVersionDto
    ) throws BookCopyNotInBuildingException {
        // TODO: 7/19/2023 getBuilding();
        Long buildingId = 1L;
        var building = new Building(buildingId);
        var bookCopy = new BookCopy();
        bookCopy.setId(id);
        bookCopy.setBuilding(building);
        bookCopy.setRowVersion(rowVersionDto.rowVersion());
        bookService.discardCopy(bookCopy);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}