package com.djordjije11.libraryappapi.service.member.impl;

import com.djordjije11.libraryappapi.exception.RecordNotCurrentVersionException;
import com.djordjije11.libraryappapi.exception.RecordNotFoundException;
import com.djordjije11.libraryappapi.exception.RequestNotValidException;
import com.djordjije11.libraryappapi.exception.member.MemberIdCardNotUniqueException;
import com.djordjije11.libraryappapi.exception.member.MemberWithLendingsDeleteException;
import com.djordjije11.libraryappapi.model.Member;
import com.djordjije11.libraryappapi.repository.lending.LendingRepository;
import com.djordjije11.libraryappapi.repository.member.MemberRepository;
import com.djordjije11.libraryappapi.service.member.MemberService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final LendingRepository lendingRepository;

    public MemberServiceImpl(MemberRepository memberRepository, LendingRepository lendingRepository) {
        this.memberRepository = memberRepository;
        this.lendingRepository = lendingRepository;
    }

    public Member create(Member member) throws MemberIdCardNotUniqueException {
        if(memberRepository.existsByIdCardNumber(member.getIdCardNumber())){
            throw new MemberIdCardNotUniqueException(member.getIdCardNumber());
        }
        return memberRepository.save(member);
    }

    @Override
    public Optional<Member> get(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    public void delete(Long id) throws MemberWithLendingsDeleteException {
        if(lendingRepository.existsByMemberId(id)){
            throw new MemberWithLendingsDeleteException();
        }
        memberRepository.deleteById(id);
    }

    @Override
    public Member update(Member member) throws RequestNotValidException, RecordNotFoundException {
        var dbMember = memberRepository.findById(member.getId())
                .orElseThrow(() -> new RecordNotFoundException(member.getId()));

        if(dbMember.getRowVersion() != member.getRowVersion()){
            throw new RecordNotCurrentVersionException(dbMember);
        }

        if(memberRepository.existsByIdCardNumberAndIdIsNot(member.getIdCardNumber(), member.getId())){
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
    public List<Member> getAll(){
        return memberRepository.findAll();
    }
}
