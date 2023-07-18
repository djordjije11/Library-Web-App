package com.djordjije11.libraryappapi.controller;

import com.djordjije11.libraryappapi.dto.book.BookShortDto;
import com.djordjije11.libraryappapi.dto.bookcopy.BookCopyCreateDto;
import com.djordjije11.libraryappapi.dto.bookcopy.BookCopyDto;
import com.djordjije11.libraryappapi.dto.bookcopy.BookCopyUpdateDto;
import com.djordjije11.libraryappapi.exception.book.BookCopyIsbnNotUniqueException;
import com.djordjije11.libraryappapi.mapper.book.BookMapper;
import com.djordjije11.libraryappapi.mapper.bookcopy.BookCopyMapper;
import com.djordjije11.libraryappapi.model.Book;
import com.djordjije11.libraryappapi.model.BookCopy;
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
    public ResponseEntity<List<BookShortDto>> getAll(){
        return ResponseEntity.ok(bookService.getAll().stream().map(bookMapper::mapShort).toList());
    }

    @GetMapping("/{bookId}/book-copy")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BookCopyDto>> getAllCopies(@PathVariable Long bookId){
        return ResponseEntity.ok(bookService.getAllCopies(bookId).stream().map(bookCopyMapper::map).toList());
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

    @PutMapping("/{bookId}/book-copy/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BookCopyDto> updateBookCopy(
            @PathVariable Long bookId,
            @PathVariable Long id,
            @Valid @RequestBody BookCopyUpdateDto bookCopyUpdateDto
    ) throws BookCopyIsbnNotUniqueException {
        BookCopy bookCopy = bookCopyMapper.map(bookCopyUpdateDto);
        bookCopy.setBook(new Book(bookId));
        bookCopy.setId(id);
        return ResponseEntity.ok(bookCopyMapper.map(bookService.updateCopy(bookCopy)));
    }

    @DeleteMapping("/{bookId}/book-copy/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> discardBookCopy(@PathVariable Long id) {
        bookService.discardCopy(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
