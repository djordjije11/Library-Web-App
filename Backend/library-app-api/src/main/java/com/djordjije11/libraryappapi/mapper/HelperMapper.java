package com.djordjije11.libraryappapi.mapper;

import com.djordjije11.libraryappapi.model.Author;
import org.mapstruct.Named;

import java.util.List;

public interface HelperMapper {
    String MAP_AUTHORS_TO_STRING = "mapAuthorsToString";
    String MAP_IDS_TO_AUTHORS = "mapIdsToAuthors";

    @Named(MAP_AUTHORS_TO_STRING)
    default String mapAuthorsToString(List<Author> authors) {
        return String.join("; ", authors.stream().map(author -> String.format("%s %s", author.getFirstname(), author.getLastname())).toList());
    }

    @Named(MAP_IDS_TO_AUTHORS)
    default List<Author> mapIdsToAuthors(List<Long> authorsIds) {
        if (authorsIds == null) {
            return null;
        }
        return authorsIds.stream().map(lId -> new Author(lId)).toList();
    }
}
