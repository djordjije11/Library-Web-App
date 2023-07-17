package com.djordjije11.libraryappapi.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Lending {
    @Version
    private long rowVersion;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDate lendingDate;
    private LocalDate returnDate;

    @ManyToOne
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_lending_book_copy"))
    private BookCopy bookCopy;
    @ManyToOne
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_lending_member"))
    private Member member;

    public Lending() {
    }

    public Lending(LocalDate lendingDate, LocalDate returnDate, BookCopy bookCopy, Member member) {
        this.lendingDate = lendingDate;
        this.returnDate = returnDate;
        this.bookCopy = bookCopy;
        this.member = member;
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

    public LocalDate getLendingDate() {
        return lendingDate;
    }

    public void setLendingDate(LocalDate lendingDate) {
        this.lendingDate = lendingDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public void setBookCopy(BookCopy bookCopy) {
        this.bookCopy = bookCopy;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
