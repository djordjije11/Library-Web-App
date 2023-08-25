package com.djordjije11.libraryappapi.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BuildingTest {
    private Building building;

    @BeforeEach
    public void init() {
        building = new Building();
    }

    @Test
    public void testSetId() {
        long id = 1;
        building.setId(id);
        assertEquals(id, building.getId());
    }

    @Test
    void testSetAddress() {
        Address address = new Address();
        building.setAddress(address);
        assertEquals(address, building.getAddress());
    }

    @Test
    void testSetEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee());
        employees.add(new Employee());
        building.setEmployees(employees);
        assertEquals(employees, building.getEmployees());
    }

    @Test
    void testEqualsNull() {
        assertNotEquals(building, null);
    }

    @Test
    void testEqualsDifferentClass() {
        Object object = new Object();
        assertNotEquals(building, object);
    }

    @Test
    void testEqualsDifferentId() {
        building.setId(1L);
        Building building2 = new Building(2L);
        assertNotEquals(building, building2);
    }

    @Test
    void testEqualsSameId() {
        building.setId(1L);
        Building building2 = new Building(1L);
        assertEquals(building, building2);
    }

    @Test
    void testHashCodeNullId() {
        assertEquals(0, building.hashCode());
    }

    @Test
    void testHashCodeNonNullId() {
        building.setId(1L);
        assertEquals(1, building.hashCode());
    }

}