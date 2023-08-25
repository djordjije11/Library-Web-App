package com.djordjije11.libraryappapi.model;

import com.djordjije11.libraryappapi.exception.ModelInvalidException;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a book author.
 * Consists of rowVersion, id, firstname, lastname, biography and books.
 *
 * @author Djordjije Radovic
 */
@Entity
public class Author {
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
     * An author's firstname. Must not be longer than 255 characters.
     */
    @Column(columnDefinition = "nvarchar(255)")
    private String firstname;
    /**
     * An author's lastname. Must not be null and must not be longer than 255 characters.
     */
    @Column(nullable = false, columnDefinition = "nvarchar(255)")
    private String lastname;
    /**
     * An author's biography.
     */
    @Column(columnDefinition = "TEXT")
    private String biography;

    /**
     * A list of author's books.
     */
    @ManyToMany(mappedBy = "authors")
//    @JsonIgnore
    private List<Book> books = new ArrayList<>();

    public Author() {
    }

    public Author(Long id) {
        setId(id);
    }

    public Author(Long id, String firstname, String lastname, String biography) {
        setId(id);
        setFirstname(firstname);
        setLastname(lastname);
        setBiography(biography);
    }

    public Author(String firstname, String lastname, String biography) {
        setFirstname(firstname);
        setLastname(lastname);
        setBiography(biography);
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
     * Returns the author's firstname.
     *
     * @return firstname of the author.
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Sets the author's firstname. Must not be longer than 255 characters.
     *
     * @param firstname of the author.
     * @throws ModelInvalidException when the author's firstname is longer than 255 characters.
     */
    public void setFirstname(String firstname) {
        if (firstname != null && firstname.length() > 255) {
            throw new ModelInvalidException(Author.class, "Author's firstname must not be longer than 255 characters.");
        }
        this.firstname = firstname;
    }

    /**
     * Returns the author's lastname.
     *
     * @return lastname of the author.
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Sets the author's lastname. Must not be null and must not be longer than 255 characters.
     *
     * @param lastname of the author.
     * @throws ModelInvalidException when the author's lastname is null or longer than 255 characters.
     */
    public void setLastname(String lastname) {
        if (lastname == null || lastname.length() > 255) {
            throw new ModelInvalidException(Author.class, "Author's lastname must not be null and must not be longer than 255 characters.");
        }
        this.lastname = lastname;
    }

    /**
     * Returns the author's biography.
     *
     * @return biography of the author.
     */
    public String getBiography() {
        return biography;
    }

    /**
     * Sets the author's biography.
     *
     * @param biography of the author.
     */
    public void setBiography(String biography) {
        this.biography = biography;
    }

    /**
     * Returns the list of author's books.
     *
     * @return books by author.
     */
    public List<Book> getBooks() {
        return books;
    }

    /**
     * Sets the list of author's books.
     *
     * @param books by author.
     */
    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
