package com.djordjije11.libraryappapi.model;

import com.djordjije11.libraryappapi.exception.ModelInvalidException;
import jakarta.persistence.*;

/**
 * Represents a city.
 * Consists of rowVersion, id, name and zipcode.
 *
 * @author Djordjije Radovic
 */
@Entity
public class City {
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
     * A city name. Must not be null and must not be longer than 100 characters.
     */
    @Column(nullable = false, columnDefinition = "nvarchar(100)")
    private String name;
    /**
     * A unique zipcode of the city. Must not be null.
     */
    @Column(nullable = false, unique = true)
    private String zipcode;

    public City() {
    }

    public City(String name, String zipcode) {
        setName(name);
        setZipcode(zipcode);
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
     * Returns the name of the city.
     *
     * @return name of the city.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the city. Must not be null and must not be longer than 100 characters.
     *
     * @param name of the city.
     * @throws ModelInvalidException when the name is null or longer than 100 characters.
     */
    public void setName(String name) {
        if (name == null || name.length() > 100) {
            throw new ModelInvalidException(City.class, "City's name must not be null and must not be longer than 100 characters.");
        }
        this.name = name;
    }

    /**
     * Returns the unique zipcode of the city.
     *
     * @return zipcode of the city.
     */
    public String getZipcode() {
        return zipcode;
    }

    /**
     * Sets the unique zipcode of the city. Must not be null.
     *
     * @param zipcode of the city.
     * @throws ModelInvalidException when the zipcode is null.
     */
    public void setZipcode(String zipcode) {
        if (zipcode == null) {
            throw new ModelInvalidException(City.class, "City's zipcode must not be null.");
        }
        this.zipcode = zipcode;
    }
}
