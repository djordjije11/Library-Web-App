package com.djordjije11.libraryappapi.service.book.impl;

import com.djordjije11.libraryappapi.service.book.BookServiceTest;
import org.junit.jupiter.api.BeforeEach;

public class BookServiceImplTest extends BookServiceTest {


    @BeforeEach
    public void init() {
        bookService = new BookServiceImpl(bookRepository, bookCopyRepository, buildingRepository, publisherRepository, authorRepository);
    }
}