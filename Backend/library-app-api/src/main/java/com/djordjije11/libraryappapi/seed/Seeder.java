package com.djordjije11.libraryappapi.seed;

import com.djordjije11.libraryappapi.model.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class Seeder implements CommandLineRunner {
    private final BuildingSeeder buildingSeeder;
    private final EmployeeSeeder employeeSeeder;
    private final BookSeeder bookSeeder;
    private final MemberSeeder memberSeeder;
    private final LendingSeeder lendingSeeder;

    public Seeder(BuildingSeeder buildingSeeder, EmployeeSeeder employeeSeeder, BookSeeder bookSeeder, MemberSeeder memberSeeder, LendingSeeder lendingSeeder){
        this.buildingSeeder = buildingSeeder;
        this.employeeSeeder = employeeSeeder;
        this.bookSeeder = bookSeeder;
        this.memberSeeder = memberSeeder;
        this.lendingSeeder = lendingSeeder;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Building> buildings = buildingSeeder.seed(10, 5);
        List<Employee> employees = employeeSeeder.seed(buildings, 50);
        List<BookCopy> bookCopies = bookSeeder.seed(buildings, 10, 50, 200);
        List<Member> members = memberSeeder.seed(80);
        List<Lending> lendings = lendingSeeder.seed(members, bookCopies,100);
    }
}
