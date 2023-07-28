package com.djordjije11.libraryappapi.seed;

import com.djordjije11.libraryappapi.helper.random.util.RandomUtil;
import com.djordjije11.libraryappapi.model.Address;
import com.djordjije11.libraryappapi.model.Building;
import com.djordjije11.libraryappapi.model.City;
import com.djordjije11.libraryappapi.repository.AddressRepository;
import com.djordjije11.libraryappapi.repository.BuildingRepository;
import com.djordjije11.libraryappapi.repository.CityRepository;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BuildingSeeder {
    private final Faker faker;
    private final CityRepository cityRepository;
    private final AddressRepository addressRepository;
    private final BuildingRepository buildingRepository;
    private final Random random;
    private final List<City> cities;
    private final List<Address> addresses;

    public BuildingSeeder(Faker faker, AddressRepository addressRepository, BuildingRepository buildingRepository, CityRepository cityRepository) {
        this.faker = faker;
        this.addressRepository = addressRepository;
        this.buildingRepository = buildingRepository;
        this.cityRepository = cityRepository;
        this.random = new Random();
        this.cities = new LinkedList<>();
        this.addresses = new LinkedList<>();
    }

    public List<Building> seed(int buildings, int cities) {
        if (buildingRepository.count() != 0) {
            return buildingRepository.findAll();
        }
        seedCities(cities);
        seedAddresses(buildings);
        return seedBuildings();
    }

    private void generateCity() {
        com.github.javafaker.Address fakeAddress = faker.address();
        City city = new City(fakeAddress.cityName(), fakeAddress.zipCode());
        cities.add(city);
    }

    private void generateAddress() {
        if (cities.isEmpty()) {
            throw new RuntimeException("There are no generated cities for addresses to be generated.");
        }
        com.github.javafaker.Address fakeAddress = faker.address();
        City city = RandomUtil.getOne(random, cities);
        Address address = new Address(fakeAddress.streetName(), Integer.parseInt(fakeAddress.streetAddressNumber()), city);
        addresses.add(address);
    }

    private void seedCities(int count) {
        for (int i = 0; i < count; i++) {
            generateCity();
        }
        cityRepository.saveAll(cities);
    }

    private void seedAddresses(int count) {
        for (int i = 0; i < count; i++) {
            generateAddress();
        }
        addressRepository.saveAll(addresses);
    }

    private List<Building> seedBuildings() {
        if (addresses.isEmpty()) {
            throw new RuntimeException("There are no generated addresses for buildings to be generated.");
        }
        List<Building> buildings = new LinkedList<>();
        for (Address address :
                addresses) {
            Building building = new Building(address);
            buildingRepository.save(building);
            buildings.add(building);
        }
        return buildings;
    }
}
