package com.djordjije11.libraryappapi.mapper.lending;

import com.djordjije11.libraryappapi.dto.lending.LendingDto;
import com.djordjije11.libraryappapi.model.Lending;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LendingMapper {
    LendingDto map(Lending lending);
}
