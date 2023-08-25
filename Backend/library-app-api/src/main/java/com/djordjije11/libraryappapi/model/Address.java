package com.djordjije11.libraryappapi.model;

import com.djordjije11.libraryappapi.exception.ModelInvalidException;
import jakarta.persistence.*;

/**
 * Represents a street address.
 * Consists of rowVersion, id, streetName and streetNumber.
 *
 * @author Djordjije Radovic
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
     * A street name. Must not be null and must not be longer than 50 characters.
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
        setStreetName(streetName);
        setStreetNumber(streetNumber);
        setCity(city);
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
     * @return unique identification number, primary key.
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
     * Returns the street name.
     *
     * @return streetName of the street of the address.
     */
    public String getStreetName() {
        return streetName;
    }

    /**
     * Sets the street name.
     * Must not be null and must not be longer than 50 characters.
     *
     * @param streetName of the street of the address.
     * @throws ModelInvalidException when the streetName is null or longer than 50 characters.
     */
    public void setStreetName(String streetName) {
        if (streetName == null || streetName.length() > 50) {
            throw new ModelInvalidException(Author.class, "Street name of the address must not be null and must not be longer than 50 characters.");
        }
        this.streetName = streetName;
    }

    /**
     * Returns the street number.
     *
     * @return streetNumber of the street of the address.
     */
    public Integer getStreetNumber() {
        return streetNumber;
    }

    /**
     * Sets the street number.
     *
     * @param streetNumber of the street of the address.
     */
    public void setStreetNumber(Integer streetNumber) {
        this.streetNumber = streetNumber;
    }

    /**
     * Returns the city of the address.
     *
     * @return city of the address.
     */
    public City getCity() {
        return city;
    }

    /**
     * Sets the city of the address.
     *
     * @param city of the address.
     */
    public void setCity(City city) {
        this.city = city;
    }
}
