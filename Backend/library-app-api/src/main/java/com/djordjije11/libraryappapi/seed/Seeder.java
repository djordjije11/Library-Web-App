package com.djordjije11.libraryappapi.seed;

import com.djordjije11.libraryappapi.model.Building;
import com.djordjije11.libraryappapi.model.Employee;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class Seeder implements CommandLineRunner {
    private final BuildingSeeder buildingSeeder;
    private final EmployeeSeeder employeeSeeder;

    public Seeder(BuildingSeeder buildingSeeder, EmployeeSeeder employeeSeeder){
        this.buildingSeeder = buildingSeeder;
        this.employeeSeeder = employeeSeeder;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Building> buildings = buildingSeeder.seed(10, 5);
        List<Employee> employees = employeeSeeder.seed(buildings, 50);
        // TODO: 7/17/2023 BookSeeder, MemberSeeder, LendingSeeder
    }
}
