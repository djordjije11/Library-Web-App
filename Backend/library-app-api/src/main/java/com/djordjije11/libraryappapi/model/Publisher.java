package com.djordjije11.libraryappapi.model;

import com.djordjije11.libraryappapi.exception.ModelInvalidException;
import jakarta.persistence.*;

/**
 * Represents a book publisher.
 * Consists of rowVersion, id and name.
 *
 * @author Djordjije Radovic
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
     * A publisher's name. Must not be null and must not be longer than 60 characters.
     */
    @Column(nullable = false, columnDefinition = "nvarchar(60)")
    private String name;

    public Publisher() {
    }

    public Publisher(Long id) {
        setId(id);
    }

    public Publisher(Long id, String name) {
        setId(id);
        setName(name);
    }

    public Publisher(String name) {
        setName(name);
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
     * Returns the publisher's name.
     *
     * @return name of the publisher.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the publisher's name. Must not be null and must not be longer than 60 characters.
     *
     * @param name of the publisher.
     * @throws ModelInvalidException when the name is null or longer than 60 characters.
     */
    public void setName(String name) {
        if (name == null || name.length() > 60) {
            throw new ModelInvalidException(Publisher.class, "Publisher's name must not be null and must not be longer than 60 characters.");
        }
        this.name = name;
    }
}
