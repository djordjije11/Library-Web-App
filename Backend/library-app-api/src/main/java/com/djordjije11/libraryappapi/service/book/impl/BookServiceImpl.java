package com.djordjije11.libraryappapi.service.book.impl;

import com.djordjije11.libraryappapi.exception.RecordNotCurrentVersionException;
import com.djordjije11.libraryappapi.exception.RecordNotFoundException;
import com.djordjije11.libraryappapi.exception.book.BookCopyIsbnNotUniqueException;
import com.djordjije11.libraryappapi.exception.book.BookCopyNotInBuildingException;
import com.djordjije11.libraryappapi.exception.book.BookWithCopiesDeleteException;
import com.djordjije11.libraryappapi.model.*;
import com.djordjije11.libraryappapi.repository.*;
import com.djordjije11.libraryappapi.service.GlobalTransactional;
import com.djordjije11.libraryappapi.service.book.BookService;
import com.djordjije11.libraryappapi.specification.book.BookSpecification;
import com.djordjije11.libraryappapi.specification.bookcopy.BookCopySpecification;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@GlobalTransactional
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookCopyRepository bookCopyRepository;
    private final BuildingRepository buildingRepository;
    private final PublisherRepository publisherRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, BookCopyRepository bookCopyRepository, BuildingRepository buildingRepository, PublisherRepository publisherRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.bookCopyRepository = bookCopyRepository;
        this.buildingRepository = buildingRepository;
        this.publisherRepository = publisherRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public Page<Book> get(String search, Pageable pageable) {
        return bookRepository.findAll(BookSpecification.bySearch(search), pageable);
    }

    @Override
    public Page<BookCopy> getCopiesAvailableInBuilding(Long bookId, Long buildingId, String search, Pageable pageable) {
        return bookCopyRepository.findAll(
                BookCopySpecification.byBook(bookId)
                        .and(BookCopySpecification.byBuilding(buildingId))
                        .and(BookCopySpecification.byOneBookSearch(search))
                        .and(BookCopySpecification.byStatus(BookCopyStatus.AVAILABLE)),
                pageable);
    }

    public Page<BookCopy> getCopiesByStatus(Long bookId, BookCopyStatus status, String search, Pageable pageable) {
        return bookCopyRepository.findAll(
                BookCopySpecification.byBook(bookId)
                        .and(BookCopySpecification.byStatus(status))
                        .and(BookCopySpecification.byOneBookSearch(search)),
                pageable);
    }

    @Override
    public Page<BookCopy> getAllBooksCopiesAvailableInBuilding(Long buildingId, String search, Pageable pageable) {
        return bookCopyRepository.findAll(
                BookCopySpecification.byStatus(BookCopyStatus.AVAILABLE)
                        .and(BookCopySpecification.byBuilding(buildingId))
                        .and(BookCopySpecification.byAllBooksSearch(search)),
                pageable);
    }

    @Override
    public Book get(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(Book.class, id));
    }

    @Override
    public BookCopy getCopy(Long id) {
        return bookCopyRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(BookCopy.class, id));
    }

    private Publisher fetchPublisher(Long id) {
        if (id == null || publisherRepository.existsById(id) == false) {
            throw new RecordNotFoundException(Publisher.class, id);
        }
        return publisherRepository.getReferenceById(id);
    }

    private List<Author> fetchAuthors(List<Author> authors) {
        List<Author> dbAuthors = new LinkedList<>();
        for (Author author :
                authors) {
            if (author.getId() == null || authorRepository.existsById(author.getId()) == false) {
                throw new RecordNotFoundException(Author.class, author.getId());
            }
            dbAuthors.add(authorRepository.getReferenceById(author.getId()));
        }
        return dbAuthors;
    }

    @Override
    public Book create(Book book) {
        if (book.getPublisher() != null) {
            book.setPublisher(fetchPublisher(book.getPublisher().getId()));
        }
        if (book.getAuthors() != null) {
            book.setAuthors(fetchAuthors(book.getAuthors()));
        }
        return bookRepository.save(book);
    }

    @Override
    public Book update(Book book) {
        Book dbBook = bookRepository.findById(book.getId())
                .orElseThrow(() -> new RecordNotFoundException(Book.class, book.getId()));

        if (dbBook.getRowVersion() != book.getRowVersion()) {
            throw new RecordNotCurrentVersionException();
        }

        Publisher publisher = book.getPublisher();
        if (publisher == null) {
            dbBook.setPublisher(null);
        } else {
            dbBook.setPublisher(fetchPublisher(publisher.getId()));
        }

        if (book.getAuthors() != null) {
            dbBook.setAuthors(fetchAuthors(book.getAuthors()));
        }

        dbBook.setTitle(book.getTitle());
        dbBook.setDescription(book.getDescription());
        dbBook.setImageUrl(book.getImageUrl());
        dbBook.setPagesNumber(book.getPagesNumber());

        return bookRepository.save(dbBook);
    }

    @Override
    public void delete(Long id) throws BookWithCopiesDeleteException {
        if (bookCopyRepository.existsByBook_Id(id)) {
            throw new BookWithCopiesDeleteException();
        }
        bookRepository.deleteById(id);
    }

    @Override
    public BookCopy createCopy(BookCopy bookCopy) throws BookCopyIsbnNotUniqueException {
        if (bookCopy.getBook() == null || bookCopy.getBook().getId() == null) {
            throw new RecordNotFoundException(Book.class);
        }
        if (bookRepository.existsById(bookCopy.getBook().getId()) == false) {
            throw new RecordNotFoundException(Book.class, bookCopy.getBook().getId());
        }

        if (bookCopy.getBuilding() == null || bookCopy.getBuilding().getId() == null) {
            throw new RecordNotFoundException(Building.class);
        }
        if (buildingRepository.existsById(bookCopy.getBuilding().getId()) == false) {
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

    //    @Transactional(Transactional.TxType.MANDATORY)
    private BookCopy fetchBookCopy(Long id) {
        if (id == null) {
            throw new RecordNotFoundException(BookCopy.class);
        }
        return bookCopyRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(BookCopy.class, id));
    }

    @Override
    public BookCopy updateCopy(BookCopy bookCopy) throws BookCopyIsbnNotUniqueException, BookCopyNotInBuildingException {
        BookCopy dbBookCopy = fetchBookCopy(bookCopy.getId());

        if (Objects.equals(dbBookCopy.getBuilding(), bookCopy.getBuilding()) == false) {
            throw new BookCopyNotInBuildingException(dbBookCopy.getId());
        }
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
    public void discardCopy(BookCopy bookCopy) throws BookCopyNotInBuildingException {
        BookCopy dbBookCopy = fetchBookCopy(bookCopy.getId());

        if (Objects.equals(dbBookCopy.getBuilding(), bookCopy.getBuilding()) == false) {
            throw new BookCopyNotInBuildingException(bookCopy.getId());
        }
        if (bookCopy.getRowVersion() != dbBookCopy.getRowVersion()) {
            throw new RecordNotCurrentVersionException();
        }

        dbBookCopy.setStatus(BookCopyStatus.LOST);
        bookCopyRepository.save(dbBookCopy);
    }

    @Override
    public Long getAvailableBookCopiesCount(Long bookId) {
        return bookCopyRepository.countByBook_IdAndStatus(bookId, BookCopyStatus.AVAILABLE);
    }
}
