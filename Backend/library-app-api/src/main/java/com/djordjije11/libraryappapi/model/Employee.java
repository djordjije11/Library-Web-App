package com.djordjije11.libraryappapi.model;

import jakarta.persistence.*;

/**
 * Represents an employee in a library.
 * Consists of rowVersion, id, idCardNumber, firstname, lastname, gender, email, building and userProfile.
 */
@Entity
public class Employee {
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
    @Column(nullable = false, unique = true, columnDefinition = "varchar(20)")
    private String idCardNumber;
    /**
     * An employee's firstname. Should not be longer than 255 characters.
     */
    @Column(nullable = false, columnDefinition = "nvarchar(255)")
    private String firstname;
    /**
     * An employee's lastname. Should not be longer than 255 characters.
     */
    @Column(nullable = false, columnDefinition = "nvarchar(255)")
    private String lastname;
    /**
     * An employee's gender. Can be male or female.
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(10) CHECK (gender in ('MALE', 'FEMALE'))")
    private Gender gender;
    /**
     * An employee's email. Should not be longer than 320 characters.
     */
    @Column(nullable = false, columnDefinition = "varchar(320)")
    private String email;
    /**
     * A building in which the employee works.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_employee_building"))
    private Building building;
    /**
     * An employee's user profile in a library system.
     */
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

    public Employee(String idCardNumber, String firstname, String lastname, Gender gender, String email, Building building, UserProfile userProfile) {
        this.idCardNumber = idCardNumber;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.email = email;
        this.building = building;
        this.userProfile = userProfile;
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
     * Returns the employee's firstname.
     * @return firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Sets the employee's firstname. Should not be longer than 255 characters.
     * @param firstname
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Returns the employee's lastname.
     * @return lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Sets the employee's lastname. Should not be longer than 255 characters.
     * @param lastname
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Returns the employee's gender.
     * @return gender
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Sets the employee's gender. Can be male or female.
     * @param gender
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Returns the employee's email.
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the employee's email. Should not be longer than 320 characters.
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the building the employee works in.
     * @return building
     */
    public Building getBuilding() {
        return building;
    }

    /**
     * Sets the building the employee works in.
     * @param building
     */
    public void setBuilding(Building building) {
        this.building = building;
    }

    /**
     * Returns the employee's user profile in the library system.
     * @return
     */
    public UserProfile getUserProfile() {
        return userProfile;
    }

    /**
     * Sets the employee's user profile in the library system.
     * @param userProfile
     */
    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }
}
