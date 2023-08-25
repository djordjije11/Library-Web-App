package com.djordjije11.libraryappapi.model;

import com.djordjije11.libraryappapi.exception.ModelInvalidException;
import jakarta.persistence.*;

import java.util.regex.Pattern;

/**
 * Represents an employee in a library.
 * Consists of rowVersion, id, idCardNumber, firstname, lastname, gender, email, building and userProfile.
 *
 * @author Djordjije Radovic
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
     * A unique person's identity card number.
     * Must not be null, must not be shorter than 10 characters and must not be longer than 20 characters and must match regex: ^\d{10,20}$ .
     */
    @Column(nullable = false, unique = true, columnDefinition = "varchar(20)")
    private String idCardNumber;
    /**
     * An employee's firstname. Must not be null and must not be longer than 255 characters.
     */
    @Column(nullable = false, columnDefinition = "nvarchar(255)")
    private String firstname;
    /**
     * An employee's lastname. Must not be null and must not be longer than 255 characters.
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
     * An employee's email. Must not be null and must not be longer than 320 characters.
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
        setIdCardNumber(idCardNumber);
        setFirstname(firstname);
        setLastname(lastname);
        setGender(gender);
        setEmail(email);
        setBuilding(building);
    }

    public Employee(String idCardNumber, String firstname, String lastname, Gender gender, String email, Building building, UserProfile userProfile) {
        setIdCardNumber(idCardNumber);
        setFirstname(firstname);
        setLastname(lastname);
        setGender(gender);
        setEmail(email);
        setBuilding(building);
        setUserProfile(userProfile);
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
            throw new ModelInvalidException(Employee.class, "Employee's must not be shorter than 10 characters and must not be longer than 20 characters and must match regex: ^\\d{10,20}$ .");
        }
        this.idCardNumber = idCardNumber;
    }

    /**
     * Returns the employee's firstname.
     *
     * @return firstname of the employee.
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Sets the employee's firstname. Must not be null and must not be longer than 255 characters.
     *
     * @param firstname of the employee.
     * @throws ModelInvalidException when the firstname is null or longer than 255 characters.
     */
    public void setFirstname(String firstname) {
        if (firstname == null || firstname.length() > 255) {
            throw new ModelInvalidException(Employee.class, "Employee's firstname must not be null and must not be longer than 255 characters.");
        }
        this.firstname = firstname;
    }

    /**
     * Returns the employee's lastname.
     *
     * @return lastname of the employee.
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Sets the employee's lastname. Must not be null and must not be longer than 255 characters.
     *
     * @param lastname of the employee.
     * @throws ModelInvalidException when the lastname is null or longer than 255 characters.
     */
    public void setLastname(String lastname) {
        if (lastname == null || lastname.length() > 255) {
            throw new ModelInvalidException(Employee.class, "Employee's lastname must not be null and must not be longer than 255 characters.");
        }
        this.lastname = lastname;
    }

    /**
     * Returns the employee's gender.
     *
     * @return gender of the employee.
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Sets the employee's gender. Can be male or female.
     *
     * @param gender of the employee.
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Returns the employee's email.
     *
     * @return email of the employee.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the employee's email. Must not be null and must not be longer than 320 characters.
     *
     * @param email of the employee.
     * @throws ModelInvalidException when the email is null or longer than 320 characters.
     */
    public void setEmail(String email) {
        if (email == null || email.length() > 320) {
            throw new ModelInvalidException(Employee.class, "Employee's email must not be null and must not be longer than 320 characters.");
        }
        this.email = email;
    }

    /**
     * Returns the building the employee works in.
     *
     * @return building the employee works in.
     */
    public Building getBuilding() {
        return building;
    }

    /**
     * Sets the building the employee works in.
     *
     * @param building the employee works in.
     */
    public void setBuilding(Building building) {
        this.building = building;
    }

    /**
     * Returns the employee's user profile in the library system.
     *
     * @return userProfile of the employee in the library system.
     */
    public UserProfile getUserProfile() {
        return userProfile;
    }

    /**
     * Sets the employee's user profile in the library system.
     *
     * @param userProfile of the employee in the library system.
     */
    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }
}
