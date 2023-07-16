package com.djordjije11.libraryappapi.repository;

import com.djordjije11.libraryappapi.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
