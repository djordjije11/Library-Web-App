package com.djordjije11.libraryappapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a copy of book in a library.
 * Consists of rowVersion, id, isbn, status, lendings, book and building.
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
     * An ISBN of book copy, a unique identification number of the book copy. Should not be null and should not be longer than 20 characters.
     */
    @Column(nullable = false, unique = true, columnDefinition = "varchar(20)")
    private String isbn;

    /**
     * A status of the book copy. Can be available, lent or lost.
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
        this.id = id;
    }

    public BookCopy(String isbn, BookCopyStatus status, Book book, Building building) {
        this.isbn = isbn;
        this.status = status;
        this.book = book;
        this.building = building;
    }

    /**
     * Returns the version of the database record.
     * @return rowVersion
     */
    public long getRowVersion() {
        return rowVersion;
    }

    /**
     * Sets the version of the database record.
     * @param rowVersion
     */
    public void setRowVersion(long rowVersion) {
        this.rowVersion = rowVersion;
    }

    /**
     * Returns the id, unique identification number.
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the id, unique identification number.
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the ISBN of book copy, the unique identification number of the book copy.
     * @return isbn
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Sets the ISBN of book copy, the unique identification number of the book copy. Should not be null and should not be longer than 20 characters.
     * @param isbn
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Returns the status of the book copy.
     * @return status
     */
    public BookCopyStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the book copy. Can be available, lent or lost.
     * @param status
     */
    public void setStatus(BookCopyStatus status) {
        this.status = status;
    }

    /**
     * Returns the book that it's the copy of.
     * @return book
     */
    public Book getBook() {
        return book;
    }

    /**
     * Sets the book that it's the copy of.
     * @param book
     */
    public void setBook(Book book) {
        this.book = book;
    }

    /**
     * Returns the building in which the book copy is.
     * @return building
     */
    public Building getBuilding() {
        return building;
    }

    /**
     * Sets the building in which the book copy is.
     * @param building
     */
    public void setBuilding(Building building) {
        this.building = building;
    }

    /**
     * Returns the list of lendings of the book copy.
     * @return lendings
     */
    public List<Lending> getLendings() {
        return lendings;
    }

    /**
     * Sets the list of lendings of the book copy.
     * @param lendings
     */
    public void setLendings(List<Lending> lendings) {
        this.lendings = lendings;
    }
}
