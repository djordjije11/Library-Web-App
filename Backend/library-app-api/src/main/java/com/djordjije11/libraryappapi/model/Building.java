package com.djordjije11.libraryappapi.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a library building.
 * Consists of id, address and employees.
 */
@Entity
public class Building {
    /**
     * A unique identification number, primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * An address of the library building.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_building_address"))
    private Address address;

    /**
     * A list of employees that work in the library building.
     */
    @OneToMany(mappedBy = "building")
//    @JsonIgnore
    private List<Employee> employees = new ArrayList<>();

    public Building() {
    }

    public Building(Long id){
        this.id = id;
    }

    public Building(Address address){
        this.address = address;
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
     * Returns the address of the library building.
     * @return address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Sets the address of the library building.
     * @param address
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Returns the list of employees that work in the library building.
     * @return employees
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the list of employees that work in the library building.
     * @param employees
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Returns if buildings are equal. Buildings are equal if theirs ids are equal.
     * @param o
     * @return true if buildings' ids are equal. Otherwise, returns false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Building building = (Building) o;

        return Objects.equals(id, building.id);
    }

    /**
     * Returns a hashcode of an id. When id is null, returns 0.
     * @return id.hashcode() when id is not null. When null, returns 0.
     */
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
