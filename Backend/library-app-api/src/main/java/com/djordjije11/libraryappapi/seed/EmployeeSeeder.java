package com.djordjije11.libraryappapi.seed;

import com.djordjije11.libraryappapi.helper.random.util.RandomUtil;
import com.djordjije11.libraryappapi.model.Building;
import com.djordjije11.libraryappapi.model.Employee;
import com.djordjije11.libraryappapi.model.Gender;
import com.djordjije11.libraryappapi.repository.EmployeeRepository;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.springframework.stereotype.Component;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Component
public class EmployeeSeeder {
    private final Faker faker;
    private final EmployeeRepository employeeRepository;
    private final Random random;

    public EmployeeSeeder(Faker faker, EmployeeRepository employeeRepository) {
        this.faker = faker;
        this.employeeRepository = employeeRepository;
        this.random = new Random();
    }

    public List<Employee> seed(List<Building> buildings, int employees) {
        List<Employee> employeesList = new LinkedList<>();
        for (int i = 0; i < employees; i++) {
            employeesList.add(seedEmployee(buildings));
        }
        employeesList.addAll(seedManualEmployees(buildings));
        return employeesList;
    }

    private Employee seedEmployee(List<Building> buildings) {
        Name fakeName = faker.name();
        Gender gender = Gender.values()[random.nextInt(Gender.values().length)];
        Building building = RandomUtil.getOne(random, buildings);
        var employee = new Employee(faker.idNumber().valid(), fakeName.firstName(), fakeName.lastName(), gender, fakeName.username(), faker.internet().emailAddress(), faker.internet().password(), building);
        return employeeRepository.save(employee);
    }

    private List<Employee> seedManualEmployees(List<Building> buildings){
        var employees = new LinkedList<Employee>();

        employees.add(
                new Employee("20190162", "Đorđije", "Radović", Gender.MALE, "djordjije11", "djordjo@gmail.com", "Lozinka10", RandomUtil.getOne(random, buildings))
        );
        employees.add(
                new Employee("20190126", "Milica", "Pantić", Gender.FEMALE, "micaCarica", "mica@gmail.com", "Lozinka11", RandomUtil.getOne(random, buildings))
        );
        employees.add(
                new Employee("20195326", "Aleksandar", "Nikolić", Gender.MALE, "AcaFaca", "coa@gmail.com", "RadnaSkela", RandomUtil.getOne(random, buildings))
        );

        return employeeRepository.saveAll(employees);
    }
}
