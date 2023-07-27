package com.djordjije11.libraryappapi.controller;

import com.djordjije11.libraryappapi.config.authentication.EmployeeClaimHolder;
import com.djordjije11.libraryappapi.controller.request.RequestPagingAndSortingParams;
import com.djordjije11.libraryappapi.controller.request.RequestSortingParamsParser;
import com.djordjije11.libraryappapi.controller.request.book.BookRequestSortingParamsParser;
import com.djordjije11.libraryappapi.controller.request.bookcopy.BookCopyRequestSortingParamsParser;
import com.djordjije11.libraryappapi.controller.response.ResponseHeadersFactory;
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
import com.djordjije11.libraryappapi.model.BookCopyStatus;
import com.djordjije11.libraryappapi.model.Building;
import com.djordjije11.libraryappapi.service.book.BookService;
import jakarta.validation.Valid;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {
    private final BookService bookService;
    private final BookMapper bookMapper = Mappers.getMapper(BookMapper.class);
    private final BookCopyMapper bookCopyMapper = Mappers.getMapper(BookCopyMapper.class);
    private final RequestSortingParamsParser bookSortingParamsParser;
    private final RequestSortingParamsParser bookCopySortingParamsParser;
    private final EmployeeClaimHolder employeeClaimHolder;

    public BookController(BookService bookService, BookRequestSortingParamsParser bookSortingParamsParser, BookCopyRequestSortingParamsParser bookCopySortingParamsParser, EmployeeClaimHolder employeeClaimHolder) {
        this.bookService = bookService;
        this.bookSortingParamsParser = bookSortingParamsParser;
        this.bookCopySortingParamsParser = bookCopySortingParamsParser;
        this.employeeClaimHolder = employeeClaimHolder;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BookShortDto>> get(
            @Valid RequestPagingAndSortingParams pagingAndSortingParams,
            @RequestParam(required = false) String search
    ) {
        Page<Book> page = bookService.get(search, pagingAndSortingParams.createPageable(bookSortingParamsParser));
        HttpHeaders httpHeaders = ResponseHeadersFactory.createWithPagination(pagingAndSortingParams.pageNumber(), pagingAndSortingParams.pageSize(), page.getTotalPages(), page.getTotalElements());
        List<BookShortDto> bookDtos = page.map(bookMapper::mapShort).toList();
        return ResponseEntity.ok().headers(httpHeaders).body(bookDtos);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GetBookDto> getById(@PathVariable Long id) {
        BookDto bookDto = bookMapper.map(bookService.get(id));
        Long availableBookCopiesCount = bookService.getAvailableBookCopiesCount(id);
        return ResponseEntity.ok(new GetBookDto(bookDto, availableBookCopiesCount));
    }

    @GetMapping("/{bookId}/book-copy")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BookCopyDto>> getAllCopiesInBuilding(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
            @PathVariable Long bookId,
            @Valid RequestPagingAndSortingParams pagingAndSortingParams,
            @RequestParam(required = false) String search
    ) {
        Long buildingId = employeeClaimHolder.getEmployeeClaim().buildingId();
        Page<BookCopy> page = bookService.getCopiesInBuilding(bookId, buildingId, search, pagingAndSortingParams.createPageable(bookCopySortingParamsParser));
        HttpHeaders httpHeaders = ResponseHeadersFactory.createWithPagination(pagingAndSortingParams.pageNumber(), pagingAndSortingParams.pageSize(), page.getTotalPages(), page.getTotalElements());
        List<BookCopyDto> bookCopyDtos = page.map(bookCopyMapper::map).toList();
        return ResponseEntity.ok().headers(httpHeaders).body(bookCopyDtos);
    }

    @GetMapping("/{bookId}/book-copy/all-buildings")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BookCopyDto>> getAllCopies(
            @PathVariable Long bookId,
            @Valid RequestPagingAndSortingParams pagingAndSortingParams,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) BookCopyStatus status
    ) {
        Page<BookCopy> page = bookService.getCopiesByStatus(bookId, status, search, pagingAndSortingParams.createPageable(bookCopySortingParamsParser));
        HttpHeaders httpHeaders = ResponseHeadersFactory.createWithPagination(pagingAndSortingParams.pageNumber(), pagingAndSortingParams.pageSize(), page.getTotalPages(), page.getTotalElements());
        List<BookCopyDto> bookCopyDtos = page.map(bookCopyMapper::map).toList();
        return ResponseEntity.ok().headers(httpHeaders).body(bookCopyDtos);
    }

    @GetMapping("/{bookId}/book-copy/{bookCopyId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BookCopyDto> getCopy(
            @PathVariable Long bookCopyId
    ) {
        return ResponseEntity.ok(bookCopyMapper.map(bookService.getCopy(bookCopyId)));
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
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
            @PathVariable Long bookId,
            @Valid @RequestBody BookCopyCreateDto bookCopyCreateDto
    ) throws BookCopyIsbnNotUniqueException {
        Long buildingId = employeeClaimHolder.getEmployeeClaim().buildingId();
        BookCopy bookCopy = bookCopyMapper.map(bookCopyCreateDto);
        bookCopy.setBuilding(new Building(buildingId));
        bookCopy.setBook(new Book(bookId));
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
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
            @PathVariable Long bookId,
            @PathVariable Long id,
            @Valid @RequestBody BookCopyUpdateDto bookCopyUpdateDto
    ) throws BookCopyIsbnNotUniqueException, BookCopyNotInBuildingException {
        Long buildingId = employeeClaimHolder.getEmployeeClaim().buildingId();
        BookCopy bookCopy = bookCopyMapper.map(bookCopyUpdateDto);
        bookCopy.setBuilding(new Building(buildingId));
        bookCopy.setBook(new Book(bookId));
        bookCopy.setId(id);
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
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
            @PathVariable Long id,
            @RequestBody RowVersionDto rowVersionDto
    ) throws BookCopyNotInBuildingException {
        Long buildingId = employeeClaimHolder.getEmployeeClaim().buildingId();
        var bookCopy = new BookCopy();
        bookCopy.setId(id);
        bookCopy.setBuilding(new Building(buildingId));
        bookCopy.setRowVersion(rowVersionDto.rowVersion());
        bookService.discardCopy(bookCopy);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
