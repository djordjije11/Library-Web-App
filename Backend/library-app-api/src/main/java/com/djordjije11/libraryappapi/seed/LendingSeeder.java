package com.djordjije11.libraryappapi.seed;

import com.djordjije11.libraryappapi.exception.RecordNotFoundException;
import com.djordjije11.libraryappapi.helper.random.util.RandomUtil;
import com.djordjije11.libraryappapi.model.*;
import com.djordjije11.libraryappapi.repository.BookCopyRepository;
import com.djordjije11.libraryappapi.repository.LendingRepository;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Component
public class LendingSeeder {
    private final LendingRepository lendingRepository;
    private final BookCopyRepository bookCopyRepository;
    private final Random random;

    private static class LendingDates {
        public LocalDate lendingDate;
        public LocalDate returnDate;

        public LendingDates(LocalDate lendingDate, LocalDate returnDate) {
            this.lendingDate = lendingDate;
            this.returnDate = returnDate;
        }

        public static LendingDates getLendingDates(LocalDate memberBirthday, Random random) {
            LocalDate dateNow = LocalDate.now();
            LocalDate randomLendingDate = memberBirthday
                    .plusYears(random.nextInt(80))
                    .plusMonths(random.nextInt(12))
                    .plusDays(random.nextInt(31));
            if (randomLendingDate.isAfter(dateNow)) {
                return new LendingDates(dateNow, null);
            }
            if (random.nextBoolean()) {
                return new LendingDates(randomLendingDate, null);
            }
            LocalDate randomReturnDate = randomLendingDate
                    .plusYears(random.nextInt(2))
                    .plusMonths(random.nextInt(12))
                    .plusDays(random.nextInt(31));
            var returnDate = randomReturnDate.isAfter(dateNow) ? null : randomReturnDate;
            return new LendingDates(randomLendingDate, returnDate);
        }
    }

    public LendingSeeder(LendingRepository lendingRepository, BookCopyRepository bookCopyRepository) {
        this.lendingRepository = lendingRepository;
        this.bookCopyRepository = bookCopyRepository;
        this.random = new Random();
    }

    public List<Lending> seed(List<Member> members, List<BookCopy> bookCopies, int lendings) {
        var lendingsList = new LinkedList<Lending>();
        for (int i = 0; i < lendings; i++) {
            lendingsList.add(seedLending(RandomUtil.getOne(random, members), RandomUtil.getOne(random, bookCopies)));
        }
        return lendingsList;
    }

    private Lending seedLending(Member member, BookCopy bookCopy) {
        LendingDates lendingDates = LendingDates.getLendingDates(member.getBirthday(), random);
        BookCopy dbBookCopy = bookCopyRepository.findById(bookCopy.getId()).get();
        if (lendingDates.returnDate == null) {
            dbBookCopy.setStatus(BookCopyStatus.LENT);
            dbBookCopy.setBuilding(null);
            bookCopyRepository.save(dbBookCopy);
        }
        var lending = new Lending(lendingDates.lendingDate, lendingDates.returnDate, dbBookCopy, member);
        return lendingRepository.save(lending);
    }
}
