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
    @Column(nullable = false, columnDefinition = "varchar(320)")
    private String email;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_employee_building"))
    private Building building;
    @OneToOne
    private UserProfile userProfile;

    public Employee() {
    }

    public Employee(String idCardNumber, String firstname, String lastname, Gender gender, String email, Building building) {
        this.idCardNumber = idCardNumber;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }
}
