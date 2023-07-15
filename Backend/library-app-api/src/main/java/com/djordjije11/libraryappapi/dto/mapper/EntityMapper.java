package com.djordjije11.libraryappapi.dto.mapper;

import com.djordjije11.libraryappapi.dto.member.MemberDto;
import com.djordjije11.libraryappapi.dto.member.SaveMemberDto;
import com.djordjije11.libraryappapi.model.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", implementationPackage = "com.djordjije11.libraryappapi.dto.mapper.impl")
public interface EntityMapper {
    MemberDto map(Member member);
    Member map(SaveMemberDto memberDto);
}
