package com.djordjije11.libraryappapi.seed;

import com.djordjije11.libraryappapi.model.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Seeder implements CommandLineRunner {
    private final SeederConfig config;
    private final BuildingSeeder buildingSeeder;
    private final EmployeeSeeder employeeSeeder;
    private final BookSeeder bookSeeder;
    private final MemberSeeder memberSeeder;
    private final LendingSeeder lendingSeeder;

    public Seeder(SeederConfig config, BuildingSeeder buildingSeeder, EmployeeSeeder employeeSeeder, BookSeeder bookSeeder, MemberSeeder memberSeeder, LendingSeeder lendingSeeder) {
        this.config = config;
        this.buildingSeeder = buildingSeeder;
        this.employeeSeeder = employeeSeeder;
        this.bookSeeder = bookSeeder;
        this.memberSeeder = memberSeeder;
        this.lendingSeeder = lendingSeeder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (config.ACTIVE == false) {
            return;
        }
        try {
            List<Building> buildings = buildingSeeder.seed(config.BUILDINGS_COUNT, config.CITIES_COUNT);
            List<Employee> employees = employeeSeeder.seed(buildings, config.EMPLOYEES_COUNT);
            List<BookCopy> bookCopies = bookSeeder.seed(buildings, config.PUBLISHER_COUNT, config.BOOK_COUNT, config.BOOK_COPY_MAX_PER_BOOK_COUNT);
            List<Member> members = memberSeeder.seed(config.MEMBER_COUNT);
            List<Lending> lendings = lendingSeeder.seed(members, bookCopies, config.LENDING_COUNT);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
