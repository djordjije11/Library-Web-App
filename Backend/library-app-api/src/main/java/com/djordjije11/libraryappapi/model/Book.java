package com.djordjije11.libraryappapi.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a book.
 * Consists of rowVersion, id, title, description, imageUrl, pagesNumber, publisher, bookCopies and authors.
 */
@Entity
public class Book {
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
     * A title of the book.
     */
    @Column(nullable = false)
    private String title;
    /**
     * A description of the book.
     */
    @Column(columnDefinition = "TEXT")
    private String description;
    /**
     * An image URL of the book's cover. Should not be longer than 2200 characters.
     */
    @Column(columnDefinition = "nvarchar(2200)")
    private String imageUrl;
    /**
     * A number of pages of the book.
     */
    private Integer pagesNumber;

    /**
     * A book's publisher.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_book_publisher"))
    private Publisher publisher;

    /**
     * A list of copies of the book.
     */
    @OneToMany(mappedBy = "book", cascade = CascadeType.PERSIST)
//    @JsonIgnore
    private List<BookCopy> bookCopies = new ArrayList<>();

    /**
     * A list of book's authors.
     */
    @ManyToMany
    @JoinTable(name = "book_author",
            joinColumns = { @JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "fk_book_author_book")) },
            inverseJoinColumns = { @JoinColumn(name = "author_id", foreignKey = @ForeignKey(name = "fk_book_author_author")) })
    private List<Author> authors = new ArrayList<>();

    public Book() {
    }

    public Book(Long id) {
        this.id = id;
    }

    public Book(String title, String description, String imageUrl, Integer pagesNumber) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.pagesNumber = pagesNumber;
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
     * Returns the title of the book.
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the book.
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the description of the book.
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the book.
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the image URL of the book's cover.
     * @return imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Sets the image URL of the book's cover. Should not be longer than 2200 characters.
     * @param imageUrl
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Returns the number of pages of the book.
     * @return pagesNumber
     */
    public Integer getPagesNumber() {
        return pagesNumber;
    }

    /**
     * Sets the number of pages of the book.
     * @param pagesNumber
     */
    public void setPagesNumber(Integer pagesNumber) {
        this.pagesNumber = pagesNumber;
    }

    /**
     * Returns the book's publisher.
     * @return publisher
     */
    public Publisher getPublisher() {
        return publisher;
    }

    /**
     * Sets the book's publisher.
     * @param publisher
     */
    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    /**
     * Returns the list of copies of the book.
     * @return bookCopies
     */
    public List<BookCopy> getBookCopies() {
        return bookCopies;
    }

    /**
     * Sets the list of copies of the book.
     * @param bookCopies
     */
    public void setBookCopies(List<BookCopy> bookCopies) {
        this.bookCopies = bookCopies;
    }

    /**
     * Returns the list of book's authors.
     * @return authors
     */
    public List<Author> getAuthors() {
        return authors;
    }

    /**
     * Sets the list of book's authors.
     * @param authors
     */
    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }
}
