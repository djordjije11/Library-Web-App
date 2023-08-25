package com.djordjije11.libraryappapi.seed;

import com.djordjije11.libraryappapi.helper.random.util.RandomUtil;
import com.djordjije11.libraryappapi.model.Gender;
import com.djordjije11.libraryappapi.model.Member;
import com.djordjije11.libraryappapi.repository.MemberRepository;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Component
public class MemberSeeder {
    private final Faker faker;
    private final MemberRepository memberRepository;
    private final Random random;

    public MemberSeeder(Faker faker, MemberRepository memberRepository) {
        this.faker = faker;
        this.memberRepository = memberRepository;
        this.random = new Random();
    }

    public List<Member> seed(int members){
        if(memberRepository.count() != 0){
            return memberRepository.findAll();
        }
        var membersList = new LinkedList<Member>();
        for (int i = 0; i < members; i++){
            membersList.add(seedMember());
        }
        membersList.addAll(seedManualMembers());
        return membersList;
    }

    private LocalDate generateBirthday(){
        return LocalDate.now()
                .minusYears(16 + random.nextInt(80))
                .minusMonths(random.nextInt(12))
                .minusDays(random.nextInt(31));
    }

    private Member seedMember(){
        Name fakeName = faker.name();
        Gender gender = Gender.values()[random.nextInt(Gender.values().length)];
        var member = new Member(RandomUtil.getDigitsString(random, random.nextInt(10, 21)), fakeName.firstName(), fakeName.lastName(), gender, faker.internet().emailAddress(), generateBirthday());
        return memberRepository.save(member);
    }

    private List<Member> seedManualMembers(){
        var members = new LinkedList<Member>();

        members.add(
                new Member(RandomUtil.getDigitsString(random, random.nextInt(10, 21)), "Đorđije", "Radović", Gender.MALE, "djordjo@gmail.com", LocalDate.of(2001, Month.FEBRUARY, 7))
        );
        members.add(
                new Member(RandomUtil.getDigitsString(random, random.nextInt(10, 21)), "Milica", "Pantić", Gender.FEMALE, "mica@gmail.com", LocalDate.of(2000, Month.AUGUST, 10))
        );
        members.add(
                new Member(RandomUtil.getDigitsString(random, random.nextInt(10, 21)), "Aleksandar", "Nikolić", Gender.MALE, "coa@gmail.com", LocalDate.of(2000, Month.MAY,2))
        );

        return memberRepository.saveAll(members);
    }
}
