package com.djordjije11.libraryappapi.model;

import com.djordjije11.libraryappapi.exception.ModelInvalidException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Represents a copy of book in a library.
 * Consists of rowVersion, id, isbn, status, lendings, book and building.
 *
 * @author Djordjije Radovic
 */
@Entity
@Table(indexes = {
        @Index(name = "book_copy_index_status", columnList = "status")
})
public class BookCopy {
    /**
     * A version of the database record.
     */
    @Version
    private long rowVersion;

    /**
     * A unique identification number, primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * An ISBN of book copy, a unique identification number of the book copy.
     * Must not be null and must not be longer than 20 characters and must match regex: ^\d{3}-\d{2}-\d{4}-\d{3}-\d{1}$ .
     */
    @Column(nullable = false, unique = true, columnDefinition = "varchar(20)")
    private String isbn;

    /**
     * A status of the book copy. Can be available, lent or lost. Must not be null.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20) CHECK (status IN ('AVAILABLE', 'LENT', 'LOST'))")
    private BookCopyStatus status;

    /**
     * A list of lendings of the book copy.
     */
    @OneToMany(mappedBy = "bookCopy")
//    @JsonIgnore
    private List<Lending> lendings = new ArrayList<>();

    /**
     * A book that it's the copy of.
     */
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_book_copy_book"))
    private Book book;

    /**
     * A building in which the book copy is.
     */
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_book_copy_building"))
    private Building building;

    public BookCopy() {
    }

    public BookCopy(Long id) {
        setId(id);
    }

    public BookCopy(String isbn, BookCopyStatus status, Book book, Building building) {
        setIsbn(isbn);
        setStatus(status);
        setBook(book);
        setBuilding(building);
    }

    /**
     * Returns the version of the database record.
     *
     * @return rowVersion version of the database record.
     */
    public long getRowVersion() {
        return rowVersion;
    }

    /**
     * Sets the version of the database record.
     *
     * @param rowVersion version of the database record.
     */
    public void setRowVersion(long rowVersion) {
        this.rowVersion = rowVersion;
    }

    /**
     * Returns the id, unique identification number.
     *
     * @return id unique identification number, primary key.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the id, unique identification number.
     *
     * @param id unique identification number, primary key.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the ISBN of book copy, the unique identification number of the book copy.
     *
     * @return isbn the unique identification number of the book copy.
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Sets the ISBN of book copy, the unique identification number of the book copy.
     * Must not be null and must not be longer than 20 characters and must match regex: ^\d{3}-\d{2}-\d{4}-\d{3}-\d{1}$ .
     *
     * @param isbn the unique identification number of the book copy.
     * @throws ModelInvalidException when the isbn is null or longer than 20 characters or does not match regex: ^\d{3}-\d{2}-\d{4}-\d{3}-\d{1}$ .
     */
    public void setIsbn(String isbn) {
        if (isbn == null || isbn.length() > 20 || Pattern.matches("^\\d{3}-\\d{2}-\\d{4}-\\d{3}-\\d{1}$", isbn) == false) {
            throw new ModelInvalidException(BookCopy.class, String.format("BookCopy's isbn must not be null and must not be longer than 20 characters and must match regex: ^\\d{3}-\\d{2}-\\d{4}-\\d{3}-\\d{1}$ . Value of the field: %s", isbn));
        }
        this.isbn = isbn;
    }

    /**
     * Returns the status of the book copy.
     *
     * @return status of the book copy.
     */
    public BookCopyStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the book copy. Can be available, lent or lost. Must not be null.
     *
     * @param status of the book copy.
     * @throws ModelInvalidException when the status is null.
     */
    public void setStatus(BookCopyStatus status) {
        if (status == null) {
            throw new ModelInvalidException(BookCopy.class, "BookCopy's status must not be null.");
        }
        this.status = status;
    }

    /**
     * Returns the book that it's the copy of.
     *
     * @return book that it's the copy of.
     */
    public Book getBook() {
        return book;
    }

    /**
     * Sets the book that it's the copy of.
     *
     * @param book that it's the copy of.
     */
    public void setBook(Book book) {
        this.book = book;
    }

    /**
     * Returns the building in which the book copy is.
     *
     * @return building in which the book copy is.
     */
    public Building getBuilding() {
        return building;
    }

    /**
     * Sets the building in which the book copy is.
     *
     * @param building in which the book copy is.
     */
    public void setBuilding(Building building) {
        this.building = building;
    }

    /**
     * Returns the list of lendings of the book copy.
     *
     * @return lendings of the book copy.
     */
    public List<Lending> getLendings() {
        return lendings;
    }

    /**
     * Sets the list of lendings of the book copy.
     *
     * @param lendings of the book copy.
     */
    public void setLendings(List<Lending> lendings) {
        this.lendings = lendings;
    }
}
