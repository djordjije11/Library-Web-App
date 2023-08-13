package com.djordjije11.libraryappapi.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a member of a library.
 * Consists of rowVersion, id, idCardNumber, firstname, lastname, gender, email, birthday and lendings.
 */
@Entity
public class Member {
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
     * A unique person's identity card number. Should not be longer than 20 characters.
     */
    @Column(unique = true, columnDefinition = "varchar(20)")
    private String idCardNumber;
    /**
     * A member's firstname. Should not be longer than 255 characters.
     */
    @Column(columnDefinition = "nvarchar(255)")
    private String firstname;
    /**
     * A member's lastname. Should not be longer than 255 characters.
     */
    @Column(columnDefinition = "nvarchar(255)")
    private String lastname;
    /**
     * A member's gender. Can be male or female.
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(10) CHECK (gender in ('MALE', 'FEMALE'))")
    private Gender gender;
    /**
     * A member's email. Should not be longer than 320 characters.
     */
    @Column(columnDefinition = "varchar(320)")
    private String email;
    /**
     * A member's birthday date.
     */
    private LocalDate birthday;

    /**
     * A list of book lendings by the member.
     */
    @OneToMany(mappedBy = "member")
    private List<Lending> lendings = new ArrayList<>();

    public Member() {
    }

    public Member(String idCardNumber, String firstname, String lastname, Gender gender, String email, LocalDate birthday) {
        this.idCardNumber = idCardNumber;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.email = email;
        this.birthday = birthday;
    }

    public Member(Long id, String idCardNumber, String firstname, String lastname, String email) {
        this.id = id;
        this.idCardNumber = idCardNumber;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
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
     * Returns the unique person's identity card number.
     * @return idCardNumber
     */
    public String getIdCardNumber() {
        return idCardNumber;
    }

    /**
     * Sets the unique person's identity card number. Should not be longer than 20 characters.
     * @param idCardNumber
     */
    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    /**
     * Returns the member's firstname.
     * @return firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Sets the member's firstname. Should not be longer than 255 characters.
     * @param firstname
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Returns the member's lastname.
     * @return lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Sets the member's lastname. Should not be longer than 255 characters.
     * @param lastname
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Returns the member's gender.
     * @return gender
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Sets the member's gender. Can be male or female.
     * @param gender
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Returns the member's email.
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the member's email. Should not be longer than 320 characters.
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the member's birthday date.
     * @return birthday
     */
    public LocalDate getBirthday() {
        return birthday;
    }

    /**
     * Sets the member's birthday date.
     * @param birthday
     */
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    /**
     * Returns the list of book lendings by the member.
     * @return lendings
     */
    public List<Lending> getLendings() {
        return lendings;
    }

    /**
     * Sets the list of book lendings by the member.
     * @param lendings
     */
    public void setLendings(List<Lending> lendings) {
        this.lendings = lendings;
    }
}
