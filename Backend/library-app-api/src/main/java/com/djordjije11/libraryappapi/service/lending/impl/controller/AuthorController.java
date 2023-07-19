package com.djordjije11.libraryappapi.service.lending.impl.controller;

import com.djordjije11.libraryappapi.dto.author.AuthorShortDto;
import com.djordjije11.libraryappapi.dto.book.BookShortDto;
import com.djordjije11.libraryappapi.mapper.author.AuthorMapper;
import com.djordjije11.libraryappapi.mapper.book.BookMapper;
import com.djordjije11.libraryappapi.service.author.AuthorService;
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
    public ResponseEntity<List<AuthorShortDto>> getAll(){
        var authors = authorService.getAll().stream().map(authorMapper::mapShort).toList();
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/{id}/books")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BookShortDto>> getBooksByAuthor(@PathVariable Long id){
        return ResponseEntity.ok(authorService.getAllBooksByAuthor(id).stream().map(bookMapper::mapShort).toList());
    }
}
