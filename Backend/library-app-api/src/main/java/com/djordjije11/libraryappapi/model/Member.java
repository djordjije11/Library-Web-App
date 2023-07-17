package com.djordjije11.libraryappapi.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member {
    @Version
    private long rowVersion;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, columnDefinition = "varchar(20)")
    private String idCardNumber;
    @Column(columnDefinition = "nvarchar(255)")
    private String firstname;
    @Column(columnDefinition = "nvarchar(255)")
    private String lastname;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(10) CHECK (gender in ('MALE', 'FEMALE'))")
    private Gender gender;
    @Column(columnDefinition = "varchar(320)")
    private String email;
    private LocalDate birthday;

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

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public List<Lending> getLendings() {
        return lendings;
    }

    public void setLendings(List<Lending> lendings) {
        this.lendings = lendings;
    }
}
