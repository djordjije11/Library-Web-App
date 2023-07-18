package com.djordjije11.libraryappapi.dto.lending;

import com.djordjije11.libraryappapi.dto.bookcopy.BookCopyDto;
import com.djordjije11.libraryappapi.dto.member.MemberShortDto;
import java.time.LocalDate;

public record LendingDto(
        long rowVersion,
        Long id,
        LocalDate lendingDate,
        LocalDate returnDate,
        BookCopyDto bookCopy,
        MemberShortDto member
) {
}
