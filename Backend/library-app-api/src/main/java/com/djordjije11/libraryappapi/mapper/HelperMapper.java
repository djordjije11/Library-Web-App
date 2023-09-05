package com.djordjije11.libraryappapi.mapper;

import com.djordjije11.libraryappapi.model.*;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface HelperMapper {
    String MAP_AUTHORS_TO_STRING = "mapAuthorsToString";
    String MAP_IDS_TO_AUTHORS = "mapIdsToAuthors";
    String MAP_ADDRESS_TO_STRING = "mapAddressToString";
    String MAP_CITY_TO_STRING = "mapCityToString";
    String MAP_BUILDING_TO_STRING = "mapBuildingToString";
    String MAP_PUBLISHER_ID_TO_PUBLISHER = "mapPublisherIdToPublisher";

    @Named(MAP_AUTHORS_TO_STRING)
    default String mapAuthorsToString(List<Author> authors) {
        return String.join("; ", authors.stream().map(author -> String.format("%s %s", author.getFirstname(), author.getLastname())).toList());
    }

    @Named(MAP_IDS_TO_AUTHORS)
    default List<Author> mapIdsToAuthors(List<Long> authorsIds) {
        if (authorsIds == null || authorsIds.size() == 0) {
            return new ArrayList<>();
        }
        return authorsIds.stream().map(lId -> new Author(lId)).toList();
    }

    @Named(MAP_ADDRESS_TO_STRING)
    default String mapBuildingToClaim(Address address){
        return String.format("%s %d", address.getStreetName(), address.getStreetNumber());
    }

    @Named(MAP_CITY_TO_STRING)
    default String mapCityToString(City city){
        return String.format("%s (%s)", city.getName(), city.getZipcode());
    }

    @Named(MAP_BUILDING_TO_STRING)
    default String mapBuildingToString(Building building) {
        if(building == null){
            return null;
        }
        final Address address = building.getAddress();
        return String.format("%s %d, %s (%s)", address.getStreetName(), address.getStreetNumber(), address.getCity().getName(), address.getCity().getZipcode());
    }

    @Named(MAP_PUBLISHER_ID_TO_PUBLISHER)
    default Publisher mapPublisherIdToPublisher(Long publisherId) {
        if(publisherId == null){
            return null;
        }
        return new Publisher(publisherId);
    }
}
