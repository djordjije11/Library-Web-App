package com.djordjije11.libraryappapi.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a book author.
 * Consists of rowVersion, id, firstname, lastname, biography and books.
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
     * An author's firstname. Should not be longer than 255 characters.
     */
    @Column(columnDefinition = "nvarchar(255)")
    private String firstname;
    /**
     * An author's lastname. Should not be longer than 255 characters.
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
        this.id = id;
    }

    public Author(Long id, String firstname, String lastname, String biography) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.biography = biography;
    }

    public Author(String firstname, String lastname, String biography){
        this.firstname = firstname;
        this.lastname = lastname;
        this.biography = biography;
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
     * Returns the author's firstname.
     * @return firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Sets the author's firstname. Should not be longer than 255 characters.
     * @param firstname
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Returns the author's lastname.
     * @return lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Sets the author's lastname. Should not be longer than 255 characters.
     * @param lastname
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Returns the author's biography.
     * @return biography
     */
    public String getBiography() {
        return biography;
    }

    /**
     * Sets the author's biography.
     * @param biography
     */
    public void setBiography(String biography) {
        this.biography = biography;
    }

    /**
     * Returns the list of author's books.
     * @return books
     */
    public List<Book> getBooks() {
        return books;
    }

    /**
     * Sets the list of author's books.
     * @param books
     */
    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
