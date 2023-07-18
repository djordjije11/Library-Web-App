package com.djordjije11.libraryappapi.service.author.impl;

import com.djordjije11.libraryappapi.model.Author;
import com.djordjije11.libraryappapi.repository.AuthorRepository;
import com.djordjije11.libraryappapi.service.GlobalTransactional;
import com.djordjije11.libraryappapi.service.author.AuthorService;
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
    public List<Author> getAll() {
        return authorRepository.findAll();
    }
}
