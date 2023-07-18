package com.djordjije11.libraryappapi.service.book.impl;

import com.djordjije11.libraryappapi.exception.RecordNotCurrentVersionException;
import com.djordjije11.libraryappapi.exception.RecordNotFoundException;
import com.djordjije11.libraryappapi.exception.book.BookCopyIsbnNotUniqueException;
import com.djordjije11.libraryappapi.model.Book;
import com.djordjije11.libraryappapi.model.BookCopy;
import com.djordjije11.libraryappapi.model.BookCopyStatus;
import com.djordjije11.libraryappapi.model.Building;
import com.djordjije11.libraryappapi.repository.BookCopyRepository;
import com.djordjije11.libraryappapi.repository.BookRepository;
import com.djordjije11.libraryappapi.repository.BuildingRepository;
import com.djordjije11.libraryappapi.service.GlobalTransactional;
import com.djordjije11.libraryappapi.service.book.BookService;
import org.springframework.stereotype.Service;
import java.util.List;

@GlobalTransactional
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookCopyRepository bookCopyRepository;
    private final BuildingRepository buildingRepository;

    public BookServiceImpl(BookRepository bookRepository, BookCopyRepository bookCopyRepository, BuildingRepository buildingRepository) {
        this.bookRepository = bookRepository;
        this.bookCopyRepository = bookCopyRepository;
        this.buildingRepository = buildingRepository;
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public List<BookCopy> getAllCopies(Long bookId) {
        return bookCopyRepository.findAllByBook_Id(bookId);
    }

    @Override
    public BookCopy createCopy(BookCopy bookCopy) throws BookCopyIsbnNotUniqueException {
        if (bookRepository.existsById(bookCopy.getBook().getId()) == false) {
            throw new RecordNotFoundException(Book.class, bookCopy.getBook().getId());
        }

        if(buildingRepository.existsById(bookCopy.getBuilding().getId()) == false){
            throw new RecordNotFoundException(Building.class, bookCopy.getBuilding().getId());
        }

        if (bookCopyRepository.existsByIsbn(bookCopy.getIsbn())) {
            throw new BookCopyIsbnNotUniqueException(bookCopy.getIsbn());
        }

        Book book = bookRepository.getReferenceById(bookCopy.getBook().getId());
        bookCopy.setBook(book);
        Building building = buildingRepository.getReferenceById(bookCopy.getBuilding().getId());
        bookCopy.setBuilding(building);

        return bookCopyRepository.save(bookCopy);
    }

    @Override
    public BookCopy updateCopy(BookCopy bookCopy) throws BookCopyIsbnNotUniqueException {
        BookCopy dbBookCopy = bookCopyRepository.findById(bookCopy.getId())
                .orElseThrow(() -> new RecordNotFoundException(BookCopy.class, bookCopy.getId()));

        if (bookCopy.getRowVersion() != dbBookCopy.getRowVersion()) {
            throw new RecordNotCurrentVersionException();
        }

        if (bookCopyRepository.existsByIsbnAndIdIsNot(bookCopy.getIsbn(), bookCopy.getId())) {
            throw new BookCopyIsbnNotUniqueException(bookCopy.getIsbn());
        }

        dbBookCopy.setIsbn(bookCopy.getIsbn());

        return bookCopyRepository.save(dbBookCopy);
    }

    @Override
    public void discardCopy(Long id) {
        BookCopy dbBookCopy = bookCopyRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(BookCopy.class, id));

        dbBookCopy.setStatus(BookCopyStatus.LOST);
        bookCopyRepository.save(dbBookCopy);
    }
}
