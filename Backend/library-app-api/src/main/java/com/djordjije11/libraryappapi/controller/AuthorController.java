package com.djordjije11.libraryappapi.controller;

import com.djordjije11.libraryappapi.controller.request.RequestPagingAndSortingParams;
import com.djordjije11.libraryappapi.controller.request.RequestSortingParamsParser;
import com.djordjije11.libraryappapi.controller.request.author.AuthorRequestSortingParamsParser;
import com.djordjije11.libraryappapi.controller.response.ResponseHeadersFactory;
import com.djordjije11.libraryappapi.dto.author.AuthorShortDto;
import com.djordjije11.libraryappapi.dto.book.BookShortDto;
import com.djordjije11.libraryappapi.mapper.author.AuthorMapper;
import com.djordjije11.libraryappapi.mapper.book.BookMapper;
import com.djordjije11.libraryappapi.model.Author;
import com.djordjije11.libraryappapi.service.author.AuthorService;
import jakarta.validation.Valid;
import org.mapstruct.factory.Mappers;
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
    private final AuthorMapper authorMapper = Mappers.getMapper(AuthorMapper.class);
    private final BookMapper bookMapper = Mappers.getMapper(BookMapper.class);
    private final RequestSortingParamsParser sortingParamsParser;

    public AuthorController(AuthorService authorService, AuthorRequestSortingParamsParser sortingParamsParser) {
        this.authorService = authorService;
        this.sortingParamsParser = sortingParamsParser;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<AuthorShortDto>> get(
            @Valid RequestPagingAndSortingParams pagingAndSortingParams,
            @RequestParam(required = false) String search
    ) {
        Page<Author> page = authorService.get(search, pagingAndSortingParams.createPageable(sortingParamsParser));
        HttpHeaders httpHeaders = ResponseHeadersFactory.createWithPagination(pagingAndSortingParams.pageNumber(), pagingAndSortingParams.pageSize(), page.getTotalPages(), page.getTotalElements());
        List<AuthorShortDto> authorDtos = page.map(authorMapper::mapShort).toList();
        return ResponseEntity.ok().headers(httpHeaders).body(authorDtos);
    }

    // TODO: 7/23/2023 NEEDS TO BE IMPROVED IF BEING USED, IMPLEMENT PAGINATION AND FILTERING
//    @GetMapping("/{id}/books")
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<List<BookShortDto>> getBooksByAuthor(@PathVariable Long id) {
//        return ResponseEntity.ok(authorService.getAllBooksByAuthor(id).stream().map(bookMapper::mapShort).toList());
//    }
}
