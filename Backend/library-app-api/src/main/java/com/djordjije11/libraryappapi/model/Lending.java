package com.djordjije11.libraryappapi.model;

import com.djordjije11.libraryappapi.exception.ModelInvalidException;
import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * Represents a lending of the book copy by the member.
 * Consists of rowVersion, id, lendingDate, returnDate, bookCopy and member.
 *
 * @author Djordjije Radovic
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
     * A date of the book copy's lending. Must not be null.
     */
    @Column(nullable = false)
    private LocalDate lendingDate;
    /**
     * A date of the book copy's returnment. Null if the book copy is not returned yet.
     */
    private LocalDate returnDate;

    /**
     * A lent book copy. Must not be null.
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_lending_book_copy"))
    private BookCopy bookCopy;
    /**
     * A member that lents the book copy. Must not be null.
     */
    @ManyToOne
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_lending_member"))
    private Member member;

    public Lending() {
    }

    public Lending(LocalDate lendingDate, BookCopy bookCopy, Member member) {
        setLendingDate(lendingDate);
        setBookCopy(bookCopy);
        setMember(member);
    }

    public Lending(LocalDate lendingDate, LocalDate returnDate, BookCopy bookCopy, Member member) {
        setLendingDate(lendingDate);
        setReturnDate(returnDate);
        setBookCopy(bookCopy);
        setMember(member);
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
     * Returns the date of the book copy's lending.
     *
     * @return lendingDate the date of the book copy's lending.
     */
    public LocalDate getLendingDate() {
        return lendingDate;
    }

    /**
     * Sets the date of the book copy's lending. Must not be null.
     *
     * @param lendingDate the date of the book copy's lending.
     * @throws ModelInvalidException when the lendingDate is null.
     */
    public void setLendingDate(LocalDate lendingDate) {
        if (lendingDate == null) {
            throw new ModelInvalidException(Lending.class, "Lending's lendingDate must not be null.");
        }
        this.lendingDate = lendingDate;
    }

    /**
     * Returns the date of the book copy's returnment.
     *
     * @return returnDate the date of the book copy's returnment.
     */
    public LocalDate getReturnDate() {
        return returnDate;
    }

    /**
     * Sets the date of the book copy's returnment. It is null if the book copy is not returned yet.
     *
     * @param returnDate the date of the book copy's returnment.
     */
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * Returns the lent book copy.
     *
     * @return bookCopy that is lent.
     */
    public BookCopy getBookCopy() {
        return bookCopy;
    }

    /**
     * Sets the lent book copy. Must not be null.
     *
     * @param bookCopy that is lent.
     * @throws ModelInvalidException when the bookCopy is null.
     */
    public void setBookCopy(BookCopy bookCopy) {
        if (bookCopy == null) {
            throw new ModelInvalidException(Lending.class, "Lending's bookCopy must not be null.");
        }
        this.bookCopy = bookCopy;
    }

    /**
     * Returns the member that lends the book copy.
     *
     * @return member that lends the book copy.
     */
    public Member getMember() {
        return member;
    }

    /**
     * Sets the member that lends the book copy. Must not be null.
     *
     * @param member that lends the book copy.
     * @throws ModelInvalidException when the member is null.
     */
    public void setMember(Member member) {
        if (member == null) {
            throw new ModelInvalidException(Lending.class, "Lending's member must not be null.");
        }
        this.member = member;
    }
}
