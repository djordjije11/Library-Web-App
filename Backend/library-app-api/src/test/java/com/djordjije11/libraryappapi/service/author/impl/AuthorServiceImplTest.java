package com.djordjije11.libraryappapi.service.author.impl;

import com.djordjije11.libraryappapi.service.author.AuthorServiceTest;
import org.junit.jupiter.api.BeforeEach;

public class AuthorServiceImplTest extends AuthorServiceTest {

    @BeforeEach
    public void init() {
        authorService = new AuthorServiceImpl(authorRepository);
    }
}