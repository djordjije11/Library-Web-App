package com.djordjije11.libraryappapi.mapper.lending;

import com.djordjije11.libraryappapi.dto.lending.LendingByMemberDto;
import com.djordjije11.libraryappapi.dto.lending.LendingDto;
import com.djordjije11.libraryappapi.mapper.HelperMapper;
import com.djordjije11.libraryappapi.model.Lending;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LendingMapper extends HelperMapper {
    @Mapping(source = "bookCopy.book.publisher.name", target = "bookCopy.book.publisher")
    @Mapping(source = "bookCopy.book.authors", target = "bookCopy.book.authors", qualifiedByName = MAP_AUTHORS_TO_STRING)
    @Mapping(source = "bookCopy.building", target = "bookCopy.building", qualifiedByName = MAP_BUILDING_TO_STRING)
    LendingDto map(Lending lending);
    @Mapping(source = "member.id", target = "memberId")
    @Mapping(source = "bookCopy.book.publisher.name", target = "bookCopy.book.publisher")
    @Mapping(source = "bookCopy.book.authors", target = "bookCopy.book.authors", qualifiedByName = MAP_AUTHORS_TO_STRING)
    @Mapping(source = "bookCopy.building", target = "bookCopy.building", qualifiedByName = MAP_BUILDING_TO_STRING)
    LendingByMemberDto mapByMember(Lending lending);
}
