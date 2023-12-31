package com.djordjije11.libraryappapi.service.member.impl;

import com.djordjije11.libraryappapi.exception.RecordNotCurrentVersionException;
import com.djordjije11.libraryappapi.exception.RecordNotFoundException;
import com.djordjije11.libraryappapi.exception.RequestNotValidException;
import com.djordjije11.libraryappapi.exception.member.MemberIdCardNotUniqueException;
import com.djordjije11.libraryappapi.exception.member.MemberWithLendingsDeleteException;
import com.djordjije11.libraryappapi.model.Member;
import com.djordjije11.libraryappapi.repository.LendingRepository;
import com.djordjije11.libraryappapi.repository.MemberRepository;
import com.djordjije11.libraryappapi.service.GlobalTransactional;
import com.djordjije11.libraryappapi.service.member.MemberService;
import com.djordjije11.libraryappapi.specification.member.MemberSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@GlobalTransactional
@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final LendingRepository lendingRepository;

    public MemberServiceImpl(MemberRepository memberRepository, LendingRepository lendingRepository) {
        this.memberRepository = memberRepository;
        this.lendingRepository = lendingRepository;
    }

    public Member create(Member member) throws MemberIdCardNotUniqueException {
        if (memberRepository.existsByIdCardNumber(member.getIdCardNumber())) {
            throw new MemberIdCardNotUniqueException(member.getIdCardNumber());
        }
        return memberRepository.save(member);
    }

    @Override
    public Member get(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(Member.class, id));
    }

    @Override
    public void delete(Long id) throws MemberWithLendingsDeleteException {
        if (lendingRepository.existsByMemberId(id)) {
            throw new MemberWithLendingsDeleteException();
        }
        memberRepository.deleteById(id);
    }

    @Override
    public Member update(Member member) throws MemberIdCardNotUniqueException, RecordNotFoundException {
        var dbMember = memberRepository.findById(member.getId())
                .orElseThrow(() -> new RecordNotFoundException(Member.class, member.getId()));

        if (dbMember.getRowVersion() != member.getRowVersion()) {
            throw new RecordNotCurrentVersionException();
        }

        boolean idCardNumberChanged = dbMember.getIdCardNumber().equals(member.getIdCardNumber()) == false;
        if (idCardNumberChanged && memberRepository.existsByIdCardNumberAndIdIsNot(member.getIdCardNumber(), member.getId())) {
            throw new MemberIdCardNotUniqueException(member.getIdCardNumber());
        }

        dbMember.setIdCardNumber(member.getIdCardNumber());
        dbMember.setFirstname(member.getFirstname());
        dbMember.setLastname(member.getLastname());
        dbMember.setGender(member.getGender());
        dbMember.setEmail(member.getEmail());
        dbMember.setBirthday(member.getBirthday());

        return memberRepository.save(member);
    }

    @Override
    public Page<Member> get(String search, Pageable pageable) {
        return memberRepository.findAll(MemberSpecification.bySearch(search), pageable);
    }
}
