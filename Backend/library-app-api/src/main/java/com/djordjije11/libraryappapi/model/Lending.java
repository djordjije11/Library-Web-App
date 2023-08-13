package com.djordjije11.libraryappapi.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Represents a lending of the book copy by the member.
 * Consists of rowVersion, id, lendingDate, returnDate, bookCopy and member.
 */
@Entity
public class Lending {
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
     * A date of the book copy's lending. Should not be null.
     */
    @Column(nullable = false)
    private LocalDate lendingDate;
    /**
     * A date of the book copy's returnment. Null if the book copy is not returned yet.
     */
    private LocalDate returnDate;

    /**
     * A lent book copy. Should not be null.
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_lending_book_copy"))
    private BookCopy bookCopy;
    /**
     * A member that lents the book copy. Should not be null.
     */
    @ManyToOne
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_lending_member"))
    private Member member;

    public Lending() {
    }

    public Lending(LocalDate lendingDate, BookCopy bookCopy, Member member) {
        this.lendingDate = lendingDate;
        this.bookCopy = bookCopy;
        this.member = member;
    }

    public Lending(LocalDate lendingDate, LocalDate returnDate, BookCopy bookCopy, Member member) {
        this.lendingDate = lendingDate;
        this.returnDate = returnDate;
        this.bookCopy = bookCopy;
        this.member = member;
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
     * Returns the date of the book copy's lending.
     * @return lendingDate
     */
    public LocalDate getLendingDate() {
        return lendingDate;
    }

    /**
     * Sets the date of the book copy's lending. Should not be null.
     * @param lendingDate
     */
    public void setLendingDate(LocalDate lendingDate) {
        this.lendingDate = lendingDate;
    }

    /**
     * Returns the date of the book copy's returnment.
     * @return returnDate
     */
    public LocalDate getReturnDate() {
        return returnDate;
    }

    /**
     * Sets the date of the book copy's returnment. It is null if the book copy is not returned yet.
     * @param returnDate
     */
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * Returns the lent book copy.
     * @return bookCopy
     */
    public BookCopy getBookCopy() {
        return bookCopy;
    }

    /**
     * Sets the lent book copy. Should not be null.
     * @param bookCopy
     */
    public void setBookCopy(BookCopy bookCopy) {
        this.bookCopy = bookCopy;
    }

    /**
     * Returns the member that lends the book copy.
     * @return member
     */
    public Member getMember() {
        return member;
    }

    /**
     * Sets the member that lends the book copy. Should not be null.
     * @param member
     */
    public void setMember(Member member) {
        this.member = member;
    }
}
