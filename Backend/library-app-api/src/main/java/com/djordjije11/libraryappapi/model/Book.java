package com.djordjije11.libraryappapi.model;

import com.djordjije11.libraryappapi.exception.ModelInvalidException;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a book.
 * Consists of rowVersion, id, title, description, imageUrl, pagesNumber, publisher, bookCopies and authors.
 *
 * @author Djordjije Radovic
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
     * A title of the book. Must not be null.
     */
    @Column(nullable = false)
    private String title;
    /**
     * A description of the book.
     */
    @Column(columnDefinition = "TEXT")
    private String description;
    /**
     * An image URL of the book's cover. Must not be longer than 2200 characters.
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
            joinColumns = {@JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "fk_book_author_book"))},
            inverseJoinColumns = {@JoinColumn(name = "author_id", foreignKey = @ForeignKey(name = "fk_book_author_author"))})
    private List<Author> authors = new ArrayList<>();

    public Book() {
    }

    public Book(Long id) {
        setId(id);
    }

    public Book(String title, String description, String imageUrl, Integer pagesNumber) {
        setTitle(title);
        setDescription(description);
        setImageUrl(imageUrl);
        setPagesNumber(pagesNumber);
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
     * Returns the title of the book.
     *
     * @return title of the book.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the book. Must not be null.
     *
     * @param title of the book.
     * @throws ModelInvalidException when the title is null.
     */
    public void setTitle(String title) {
        if (title == null) {
            throw new ModelInvalidException(Book.class, "Book's title must not be null.");
        }
        this.title = title;
    }

    /**
     * Returns the description of the book.
     *
     * @return description of the book.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the book.
     *
     * @param description of the book.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the image URL of the book's cover.
     *
     * @return imageUrl of the book's cover.
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Sets the image URL of the book's cover. Must not be longer than 2200 characters.
     *
     * @param imageUrl of the book's cover.
     * @throws ModelInvalidException when the imageUrl is longer than 2200 characters.
     */
    public void setImageUrl(String imageUrl) {
        if (imageUrl != null && imageUrl.length() > 2200) {
            throw new ModelInvalidException(Book.class, "Book's imageUrl must not be longer than 2200 characters.");
        }
        this.imageUrl = imageUrl;
    }

    /**
     * Returns the number of pages of the book.
     *
     * @return pagesNumber of the book.
     */
    public Integer getPagesNumber() {
        return pagesNumber;
    }

    /**
     * Sets the number of pages of the book.
     *
     * @param pagesNumber of the book.
     */
    public void setPagesNumber(Integer pagesNumber) {
        this.pagesNumber = pagesNumber;
    }

    /**
     * Returns the book's publisher.
     *
     * @return publisher of the book.
     */
    public Publisher getPublisher() {
        return publisher;
    }

    /**
     * Sets the book's publisher.
     *
     * @param publisher of the book.
     */
    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    /**
     * Returns the list of copies of the book.
     *
     * @return bookCopies of the book.
     */
    public List<BookCopy> getBookCopies() {
        return bookCopies;
    }

    /**
     * Sets the list of copies of the book.
     *
     * @param bookCopies of the book.
     */
    public void setBookCopies(List<BookCopy> bookCopies) {
        this.bookCopies = bookCopies;
    }

    /**
     * Returns the list of book's authors.
     *
     * @return authors that wrote the book.
     */
    public List<Author> getAuthors() {
        return authors;
    }

    /**
     * Sets the list of book's authors.
     *
     * @param authors that wrote the book.
     */
    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }
}
