package com.djordjije11.libraryappapi.repository;

import com.djordjije11.libraryappapi.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
