package com.djordjije11.libraryappapi.dto.lending;

import com.djordjije11.libraryappapi.dto.bookcopy.BookCopyDto;

import java.time.LocalDate;

public record LendingByMemberDto(
        long rowVersion,
        Long id,
        LocalDate lendingDate,
        LocalDate returnDate,
        BookCopyDto bookCopy,
        Long memberId
) {
}
