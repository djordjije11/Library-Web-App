package com.djordjije11.libraryappapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(indexes = {
        @Index(name = "book_copy_index_status", columnList = "status")
})
public class BookCopy {
    @Version
    private long rowVersion;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, columnDefinition = "varchar(20)")
    private String isbn;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20) CHECK (status IN ('AVAILABLE', 'LENT', 'LOST'))")
    private BookCopyStatus status;

    @OneToMany(mappedBy = "bookCopy")
//    @JsonIgnore
    private List<Lending> lendings = new ArrayList<>();

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_book_copy_book"))
    private Book book;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_book_copy_building"))
    private Building building;

    public BookCopy() {
    }

    public BookCopy(String isbn, BookCopyStatus status, Book book, Building building) {
        this.isbn = isbn;
        this.status = status;
        this.book = book;
        this.building = building;
    }

    public long getRowVersion() {
        return rowVersion;
    }

    public void setRowVersion(long rowVersion) {
        this.rowVersion = rowVersion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public BookCopyStatus getStatus() {
        return status;
    }

    public void setStatus(BookCopyStatus status) {
        this.status = status;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public List<Lending> getLendings() {
        return lendings;
    }

    public void setLendings(List<Lending> lendings) {
        this.lendings = lendings;
    }
}
