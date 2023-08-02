package com.djordjije11.libraryappapi.mapper.building;

import com.djordjije11.libraryappapi.config.authentication.claim.BuildingClaim;
import com.djordjije11.libraryappapi.mapper.HelperMapper;
import com.djordjije11.libraryappapi.model.Building;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BuildingMapper extends HelperMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "building.address", target = "street", qualifiedByName = MAP_ADDRESS_TO_STRING)
    @Mapping(source = "building.address.city", target = "city", qualifiedByName = MAP_CITY_TO_STRING)
    BuildingClaim mapClaim(Building building);
}
