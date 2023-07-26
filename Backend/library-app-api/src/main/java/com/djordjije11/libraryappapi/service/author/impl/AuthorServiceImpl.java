package com.djordjije11.libraryappapi.service.author.impl;

import com.djordjije11.libraryappapi.exception.RecordNotFoundException;
import com.djordjije11.libraryappapi.model.Author;
import com.djordjije11.libraryappapi.model.Book;
import com.djordjije11.libraryappapi.repository.AuthorRepository;
import com.djordjije11.libraryappapi.service.GlobalTransactional;
import com.djordjije11.libraryappapi.service.author.AuthorService;
import com.djordjije11.libraryappapi.specification.author.AuthorSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@GlobalTransactional
@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Page<Author> get(String search, Pageable pageable) {
        return authorRepository.findAll(AuthorSpecification.bySearch(search), pageable);
    }

    // TODO: 7/23/2023 NEEDS TO BE IMPROVED IF BEING USED, IMPLEMENT PAGINATION AND FILTERING
    @Override
    public List<Book> getAllBooksByAuthor(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(Author.class, id));
        return author.getBooks();
    }
}
