package com.djordjije11.libraryappapi.controller;

import com.djordjije11.libraryappapi.controller.request.RequestQueryParams;
import com.djordjije11.libraryappapi.controller.response.ResponseHeadersFactory;
import com.djordjije11.libraryappapi.dto.author.AuthorShortDto;
import com.djordjije11.libraryappapi.dto.book.BookShortDto;
import com.djordjije11.libraryappapi.mapper.author.AuthorMapper;
import com.djordjije11.libraryappapi.mapper.book.BookMapper;
import com.djordjije11.libraryappapi.model.Author;
import com.djordjije11.libraryappapi.service.author.AuthorService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/author")
public class AuthorController {
    private final AuthorService authorService;
    private final AuthorMapper authorMapper;
    private final BookMapper bookMapper;

    public AuthorController(AuthorService authorService, AuthorMapper authorMapper, BookMapper bookMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
        this.bookMapper = bookMapper;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<AuthorShortDto>> get(
            @Valid RequestQueryParams queryParams
    ) {
        Page<Author> page = authorService.get(queryParams.createPageable(), queryParams.search());
        HttpHeaders httpHeaders = ResponseHeadersFactory.createWithPagination(queryParams.pageNumber(), queryParams.pageSize(), page.getTotalPages(), page.getTotalElements());
        List<AuthorShortDto> authorDtos = page.map(authorMapper::mapShort).toList();
        return ResponseEntity.ok().headers(httpHeaders).body(authorDtos);
    }

    // TODO: 7/23/2023 NEEDS TO BE IMPROVED IF BEING USED, IMPLEMENT PAGINATION AND FILTERING
    @GetMapping("/{id}/books")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BookShortDto>> getBooksByAuthor(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.getAllBooksByAuthor(id).stream().map(bookMapper::mapShort).toList());
    }
}
