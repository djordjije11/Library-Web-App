package com.djordjije11.libraryappapi.service.book;

import com.djordjije11.libraryappapi.exception.RecordNotCurrentVersionException;
import com.djordjije11.libraryappapi.exception.book.BookCopyIsbnNotUniqueException;
import com.djordjije11.libraryappapi.exception.book.BookCopyNotInBuildingException;
import com.djordjije11.libraryappapi.exception.book.BookWithCopiesDeleteException;
import com.djordjije11.libraryappapi.exception.RecordNotFoundException;
import com.djordjije11.libraryappapi.model.Book;
import com.djordjije11.libraryappapi.model.BookCopy;
import com.djordjije11.libraryappapi.model.BookCopyStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Represents the service layer for a Book and BookCopy classes.
 */
public interface BookService {
    /**
     * Returns the page of books filtered by search text and selected by options of Pageable instance.
     *
     * @param search   by book's title, book's publisher's name, book's author's firstname or lastname.
     * @param pageable contains options for skipping and taking database records and sorting them.
     * @return page of books filtered by search text and selected by options of Pageable instance.
     */
    Page<Book> get(String search, Pageable pageable);

    /**
     * Returns the page of book copies by book's id that are available in the specified building filtered by search text and selected by options of Pageable instance.
     *
     * @param bookId     the id of the book which of the copies are.
     * @param buildingId the id of the building in which are the book copies.
     * @param search     by book copy's ISBN.
     * @param pageable   contains options for skipping and taking database records and sorting them.
     * @return page of book copies by book's id that are available in the specified building filtered by search text and selected by options of Pageable instance.
     */
    Page<BookCopy> getCopiesAvailableInBuilding(Long bookId, Long buildingId, String search, Pageable pageable);

    /**
     * Returns the page of book copies by book's id filtered by status, search text and selected by options of Pageable instance.
     *
     * @param bookId   the id of the book which of the copies are.
     * @param status   the status of the book copies.
     * @param search   by book copy's ISBN.
     * @param pageable contains options for skipping and taking database records and sorting them.
     * @return page of book copies by book's id filtered by status, search text and selected by options of Pageable instance.
     */
    Page<BookCopy> getCopiesByStatus(Long bookId, BookCopyStatus status, String search, Pageable pageable);

    /**
     * Returns the page of book copies that are available in the specified building filtered by search text and selected by options of Pageable instance.
     *
     * @param buildingId the id of the building in which are the book copies.
     * @param search     by book copy's ISBN, book's title, book's publisher's name, book's author's firstname or lastname.
     * @param pageable   contains options for skipping and taking database records and sorting them.
     * @return page of book copies that are available in the specified building filtered by search text and selected by options of Pageable instance.
     */
    Page<BookCopy> getAllBooksCopiesAvailableInBuilding(Long buildingId, String search, Pageable pageable);

    /**
     * Returns the book by id. Throws RecordNotFoundException when the book does not exist in the library system's database.
     *
     * @param id the id of the book.
     * @return book by id
     * @throws RecordNotFoundException when the book does not exist in the library system's database.
     */
    Book get(Long id);

    /**
     * Returns the book copy by id. Throws RecordNotFoundException when the book copy does not exist in the library system's database.
     *
     * @param id the id of the book copy.
     * @return book copy by id
     * @throws RecordNotFoundException when the book copy does not exist in the library system's database.
     */
    BookCopy getCopy(Long id);

    /**
     * Saves the book to the library system's database and returns the saved book.
     *
     * @param book the book to be saved.
     * @return saved book.
     * @throws RecordNotFoundException when the book's publisher or any of the book's authors does not exist in the library system's database.
     */
    Book create(Book book);

    /**
     * Updates the book in the library system's database and returns the updated book.
     * Throws:
     * RecordNotFoundException when the book, book's publisher or any of the book's authors does not exist in the library system's database,
     * RecordNotCurrentVersionException when the record's rowVersion is not up-to-date.
     *
     * @param book the book to be updated.
     * @return updated book.
     * @throws RecordNotFoundException          when the book, book's publisher or any of the book's authors does not exist in the library system's database.
     * @throws RecordNotCurrentVersionException when the record's rowVersion is not up-to-date.
     */
    Book update(Book book);

    /**
     * Deletes the book by id. Throws BookWithCopiesDeleteException when the copies of the book already exist in the library system's database.
     *
     * @param id the id of the book to be deleted.
     * @throws BookWithCopiesDeleteException when the copies of the book already exist in the library system's database.
     */
    void delete(Long id) throws BookWithCopiesDeleteException;

    /**
     * Saves the book copy to the library system's database and returns the saved book copy.
     * Throws:
     * RecordNotFoundException when the book of which the copy is or the building in which the copy is does not exist in the library system's database or if they are null,
     * BookCopyIsbnNotUniqueException when the book copy's ISBN is not unique.
     *
     * @param bookCopy the book copy to be saved.
     * @return saved book copy.
     * @throws RecordNotFoundException        when the book of which the copy is or the building in which the copy is does not exist in the library system's database or if they are null.
     * @throws BookCopyIsbnNotUniqueException when the book copy's ISBN is not unique.
     */
    BookCopy createCopy(BookCopy bookCopy) throws BookCopyIsbnNotUniqueException;

    /**
     * Updates the book copy to the library system's database and returns the updated book copy.
     * Throws:
     * RecordNotFoundException when the book copy does not exist in the library system's database,
     * BookCopyNotInBuildingException when the book copy to be updated is not in the same building as requested,
     * RecordNotCurrentVersionException when the record's rowVersion is not up-to-date,
     * BookCopyIsbnNotUniqueException when the book copy's ISBN is not unique.
     *
     * @param bookCopy the book copy to be updated.
     * @return updated book copy.
     * @throws RecordNotFoundException          when the book copy does not exist in the library system's database.
     * @throws BookCopyNotInBuildingException   when the book copy to be updated is not in the same building as requested.
     * @throws RecordNotCurrentVersionException when the record's rowVersion is not up-to-date.
     * @throws BookCopyIsbnNotUniqueException   when the book copy's ISBN is not unique.
     */
    BookCopy updateCopy(BookCopy bookCopy) throws BookCopyIsbnNotUniqueException, BookCopyNotInBuildingException;

    /**
     * Updates the book copy's status to LOST in the library system's database.
     * Throws:
     * RecordNotFoundException when the book copy does not exist in the library system's database,
     * BookCopyNotInBuildingException when the book copy to be updated is not in the same building as requested,
     * RecordNotCurrentVersionException when the record's rowVersion is not up-to-date.
     *
     * @param bookCopy
     * @throws RecordNotFoundException          when the book copy does not exist in the library system's database.
     * @throws BookCopyNotInBuildingException   when the book copy to be updated is not in the same building as requested.
     * @throws RecordNotCurrentVersionException when the record's rowVersion is not up-to-date.
     */
    void discardCopy(BookCopy bookCopy) throws BookCopyNotInBuildingException;

    /**
     * Returns the count of book copies by the book's id that are available in the building.
     *
     * @param bookId     the book's id of which the copies are.
     * @param buildingId the building's id where the copies are.
     * @return count of book copies by the book's id that are available in the building.
     */
    Long getAvailableBookCopiesInBuildingCount(Long bookId, Long buildingId);
}
