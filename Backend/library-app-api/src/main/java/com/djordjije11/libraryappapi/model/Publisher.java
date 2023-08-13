package com.djordjije11.libraryappapi.model;

import jakarta.persistence.*;

/**
 * Represents a book publisher.
 * Consists of rowVersion, id and name.
 */
@Entity
public class Publisher {
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
     * A publisher's name. Should not be null and should not be longer than 60 characters.
     */
    @Column(nullable = false, columnDefinition = "nvarchar(60)")
    private String name;

    public Publisher() {
    }

    public Publisher(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Publisher(String name){
        this.name = name;
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
     * Returns the publisher's name.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the publisher's name. Should not be null and should not be longer than 60 characters.
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
}
