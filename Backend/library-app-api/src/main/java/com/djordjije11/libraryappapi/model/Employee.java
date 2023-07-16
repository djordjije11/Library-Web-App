package com.djordjije11.libraryappapi.model;

import jakarta.persistence.*;

@Entity
public class Employee {
    @Version
    private long rowVersion;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, columnDefinition = "varchar(20)")
    private String idCardNumber;
    @Column(nullable = false, columnDefinition = "nvarchar(255)")
    private String firstname;
    @Column(nullable = false, columnDefinition = "nvarchar(255)")
    private String lastname;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(10) CHECK (gender in ('MALE', 'FEMALE'))")
    private Gender gender;
    @Column(nullable = false, unique = true, columnDefinition = "varchar(30)")
    private String username;
    @Column(nullable = false, columnDefinition = "varchar(320)")
    private String email;
    @Column(nullable = false, columnDefinition = "nvarchar(72)")
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_employee_building"))
    private Building building;

    public Employee() {
    }

    public Employee(String idCardNumber, String firstname, String lastname, Gender gender, String username, String email, String password, Building building) {
        this.idCardNumber = idCardNumber;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.username = username;
        this.email = email;
        this.password = password;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }
}
