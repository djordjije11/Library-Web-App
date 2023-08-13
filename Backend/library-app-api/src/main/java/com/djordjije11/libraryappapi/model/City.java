package com.djordjije11.libraryappapi.model;

import jakarta.persistence.*;

/**
 * Represents a city.
 * Consists of rowVersion, id, name and zipcode.
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
     * A city name. Should not be null and should not be longer than 100 characters.
     */
    @Column(nullable = false, columnDefinition = "nvarchar(100)")
    private String name;
    /**
     * A unique zipcode of the city. Should not be null.
     */
    @Column(nullable = false, unique = true)
    private String zipcode;

    public City() {
    }

    public City(String name, String zipcode){
        this.name = name;
        this.zipcode = zipcode;
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
     * Returns the name of the city.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the city. Should not be null and should not be longer than 100 characters.
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the unique zipcode of the city.
     * @return zipcode
     */
    public String getZipcode() {
        return zipcode;
    }

    /**
     * Sets the unique zipcode of the city. Should not be null.
     * @param zipcode
     */
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
