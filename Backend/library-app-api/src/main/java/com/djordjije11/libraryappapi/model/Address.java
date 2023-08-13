package com.djordjije11.libraryappapi.model;

import jakarta.persistence.*;

/**
 * Represents a street address.
 * Consists of rowVersion, id, streetName and streetNumber.
 */
@Entity
public class Address {
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
     * A street name. Should not be null and should not be longer than 50 characters.
     */
    @Column(nullable = false, columnDefinition = "nvarchar(50)")
    private String streetName;
    /**
     * A street number.
     */
    private Integer streetNumber;

    /**
     * The city of the address.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_address_city"))
    private City city;

    public Address() {
    }

    public Address(String streetName, Integer streetNumber, City city) {
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.city = city;
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
     * Returns the street name.
     * @return streetName
     */
    public String getStreetName() {
        return streetName;
    }

    /**
     * Sets the street name. Should not be null and should not be longer than 50 characters.
     * @param streetName
     */
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    /**
     * Returns the street number.
     * @return streetNumber
     */
    public Integer getStreetNumber() {
        return streetNumber;
    }

    /**
     * Sets the street number.
     * @param streetNumber
     */
    public void setStreetNumber(Integer streetNumber) {
        this.streetNumber = streetNumber;
    }

    /**
     * Returns the city of the address.
     * @return city
     */
    public City getCity() {
        return city;
    }

    /**
     * Sets the city of the address.
     * @param city
     */
    public void setCity(City city) {
        this.city = city;
    }
}
