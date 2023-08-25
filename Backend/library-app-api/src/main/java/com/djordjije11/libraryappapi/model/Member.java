package com.djordjije11.libraryappapi.model;

import com.djordjije11.libraryappapi.exception.ModelInvalidException;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Represents a member of a library.
 * Consists of rowVersion, id, idCardNumber, firstname, lastname, gender, email, birthday and lendings.
 *
 * @author Djordjije Radovic
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
     * A unique person's identity card number.
     * Must not be null, must not be shorter than 10 characters and must not be longer than 20 characters and must match regex: ^\d{10,20}$ .
     */
    @Column(unique = true, columnDefinition = "varchar(20)")
    private String idCardNumber;
    /**
     * A member's firstname. Must not be longer than 255 characters.
     */
    @Column(columnDefinition = "nvarchar(255)")
    private String firstname;
    /**
     * A member's lastname. Must not be longer than 255 characters.
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
     * A member's email. Must not be longer than 320 characters.
     */
    @Column(columnDefinition = "varchar(320)")
    private String email;
    /**
     * A member's birthday date. A member must be at least 16 years old.
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
        setIdCardNumber(idCardNumber);
        setFirstname(firstname);
        setLastname(lastname);
        setGender(gender);
        setEmail(email);
        setBirthday(birthday);
    }

    public Member(Long id, String idCardNumber, String firstname, String lastname, String email) {
        setId(id);
        setIdCardNumber(idCardNumber);
        setFirstname(firstname);
        setLastname(lastname);
        setEmail(email);
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
     * Returns the unique person's identity card number.
     *
     * @return idCardNumber the unique person's identity card number.
     */
    public String getIdCardNumber() {
        return idCardNumber;
    }

    /**
     * Sets the unique person's identity card number.
     * Must not be null, must not be shorter than 10 characters and must not be longer than 20 characters and must match regex: ^\d{10,20}$ .
     *
     * @param idCardNumber the unique person's identity card number.
     * @throws ModelInvalidException when the idCardNumber is null or shorter than 10 characters or longer than 20 characters or does not match regex: ^\d{10,20}$ .
     */
    public void setIdCardNumber(String idCardNumber) {
        if (idCardNumber == null || idCardNumber.length() < 10 || idCardNumber.length() > 20 || Pattern.matches("^\\d{10,20}$", idCardNumber) == false) {
            throw new ModelInvalidException(Member.class, "Member's idCardNumber must not be shorter than 10 characters and must not be longer than 20 characters and must match regex: ^\\d{10,20}$ .");
        }
        this.idCardNumber = idCardNumber;
    }

    /**
     * Returns the member's firstname.
     *
     * @return firstname of the member.
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Sets the member's firstname. Must not be longer than 255 characters.
     *
     * @param firstname of the member.
     * @throws ModelInvalidException when the firstname is longer than 255 characters.
     */
    public void setFirstname(String firstname) {
        if (firstname != null && firstname.length() > 255) {
            throw new ModelInvalidException(Member.class, "Member's firstname must not be longer than 255 characters.");
        }
        this.firstname = firstname;
    }

    /**
     * Returns the member's lastname.
     *
     * @return lastname of the member.
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Sets the member's lastname. Must not be longer than 255 characters.
     *
     * @param lastname of the member.
     * @throws ModelInvalidException when the lastname is longer than 255 characters.
     */
    public void setLastname(String lastname) {
        if (lastname != null && lastname.length() > 255) {
            throw new ModelInvalidException(Member.class, "Member's lastname must not be longer than 255 characters.");
        }
        this.lastname = lastname;
    }

    /**
     * Returns the member's gender.
     *
     * @return gender of the member.
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Sets the member's gender. Can be male or female.
     *
     * @param gender of the member.
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Returns the member's email.
     *
     * @return email of the member.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the member's email. Must not be longer than 320 characters.
     *
     * @param email of the member.
     * @throws ModelInvalidException when the email is longer than 320 characters.
     */
    public void setEmail(String email) {
        if (email != null && email.length() > 320) {
            throw new ModelInvalidException(Member.class, "Member's email must not be longer than 320 characters.");
        }
        this.email = email;
    }

    /**
     * Returns the member's birthday date.
     *
     * @return birthday of the member.
     */
    public LocalDate getBirthday() {
        return birthday;
    }

    /**
     * Sets the member's birthday date. A member must be at least 16 years old.
     *
     * @param birthday of the member.
     * @throws ModelInvalidException when the birthday is from less than 16 years ago.
     */
    public void setBirthday(LocalDate birthday) {
        if (birthday != null && birthday.isAfter(LocalDate.now().minusYears(16))) {
            throw new ModelInvalidException(Member.class, "A member must be at least 16 years old.");
        }
        this.birthday = birthday;
    }

    /**
     * Returns the list of book lendings by the member.
     *
     * @return lendings by the member.
     */
    public List<Lending> getLendings() {
        return lendings;
    }

    /**
     * Sets the list of book lendings by the member.
     *
     * @param lendings by the member.
     */
    public void setLendings(List<Lending> lendings) {
        this.lendings = lendings;
    }
}
