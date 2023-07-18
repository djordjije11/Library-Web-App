package com.djordjije11.libraryappapi.mapper.member;

import com.djordjije11.libraryappapi.dto.member.MemberCreateDto;
import com.djordjije11.libraryappapi.dto.member.MemberDto;
import com.djordjije11.libraryappapi.dto.member.MemberShortDto;
import com.djordjije11.libraryappapi.dto.member.MemberUpdateDto;
import com.djordjije11.libraryappapi.model.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    MemberDto mapDetail(Member member);
    MemberShortDto mapShort(Member member);
    Member map(MemberUpdateDto memberDto);
    Member map(MemberCreateDto memberDto);
}
