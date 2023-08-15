package com.djordjije11.libraryappapi.seed;

import com.djordjije11.libraryappapi.helper.random.util.RandomUtil;
import com.djordjije11.libraryappapi.model.*;
import com.djordjije11.libraryappapi.repository.EmployeeRepository;
import com.djordjije11.libraryappapi.repository.UserProfileRepository;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Component
public class EmployeeSeeder {
    private final Faker faker;
    private final EmployeeRepository employeeRepository;
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final Random random;

    public EmployeeSeeder(Faker faker, EmployeeRepository employeeRepository, UserProfileRepository userProfileRepository, PasswordEncoder passwordEncoder) {
        this.faker = faker;
        this.employeeRepository = employeeRepository;
        this.userProfileRepository = userProfileRepository;
        this.passwordEncoder = passwordEncoder;
        this.random = new Random();
    }

    public List<Employee> seed(List<Building> buildings, int employees) {
        if (employeeRepository.count() != 0) {
            return employeeRepository.findAll();
        }
        List<Employee> employeesList = new LinkedList<>();
        for (int i = 0; i < employees; i++) {
            employeesList.add(seedEmployee(buildings));
        }
        employeesList.addAll(seedManualEmployees(buildings));
        return employeesList;
    }

    private Employee seedEmployee(List<Building> buildings) {
        Name fakeName = faker.name();
        var userProfile = new UserProfile(fakeName.username(), passwordEncoder.encode(faker.internet().password()), UserRole.EMPLOYEE);
        UserProfile dbUserProfile = userProfileRepository.save(userProfile);
        Gender gender = Gender.values()[random.nextInt(Gender.values().length)];
        Building building = RandomUtil.getOne(random, buildings);
        var employee = new Employee(RandomUtil.getDigitsString(random, random.nextInt(10, 21)), fakeName.firstName(), fakeName.lastName(), gender, faker.internet().emailAddress(), building, dbUserProfile);
        return employeeRepository.save(employee);
    }

    private List<Employee> seedManualEmployees(List<Building> buildings) {
        var employees = new LinkedList<Employee>();

        var userProfile1 = new UserProfile("Djole10", passwordEncoder.encode("DjoleDjole10"), UserRole.EMPLOYEE);
        userProfileRepository.save(userProfile1);
        var userProfile2 = new UserProfile("Mica333", passwordEncoder.encode("Micaa333"), UserRole.EMPLOYEE);
        userProfileRepository.save(userProfile2);
        var userProfile3 = new UserProfile("AcaAca", passwordEncoder.encode("RadnaSkela10"), UserRole.EMPLOYEE);
        userProfileRepository.save(userProfile3);

        employees.add(
                new Employee(RandomUtil.getDigitsString(random, random.nextInt(10, 21)), "Đorđije", "Radović", Gender.MALE, "djordjo@gmail.com",
                        RandomUtil.getOne(random, buildings),
                        userProfile1)
        );
        employees.add(
                new Employee(RandomUtil.getDigitsString(random, random.nextInt(10, 21)), "Milica", "Pantić", Gender.FEMALE, "mica@gmail.com",
                        RandomUtil.getOne(random, buildings),
                        userProfile2)
        );
        employees.add(
                new Employee(RandomUtil.getDigitsString(random, random.nextInt(10, 21)), "Aleksandar", "Nikolić", Gender.MALE, "coa@gmail.com",
                        RandomUtil.getOne(random, buildings),
                        userProfile3)
        );

        return employeeRepository.saveAll(employees);
    }
}
