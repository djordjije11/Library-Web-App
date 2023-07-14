package com.djordjije11.libraryappapi.controller;

import com.djordjije11.libraryappapi.dto.SaveMemberDto;
import com.djordjije11.libraryappapi.exception.RecordNotFoundException;
import com.djordjije11.libraryappapi.exception.RecordNotValidException;
import com.djordjije11.libraryappapi.model.Member;
import com.djordjije11.libraryappapi.repository.member.MemberReadonlyRepository;
import com.djordjije11.libraryappapi.service.member.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/member")
public class MemberController {
    private final MemberReadonlyRepository memberReadonlyRepository;
    private final MemberService memberService;

    public MemberController(MemberReadonlyRepository memberReadonlyRepository, MemberService memberService) {
        this.memberReadonlyRepository = memberReadonlyRepository;
        this.memberService = memberService;
    }

    @GetMapping
    public List<Member> getAll(){
        return memberReadonlyRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Member getById(@PathVariable Long id){
        return memberReadonlyRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Member not found."));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@Valid @RequestBody SaveMemberDto memberDto){
        Member member = new Member(){{
            setIdCardNumber(memberDto.idCardNumber());
            setFirstname(memberDto.firstname());
            setLastname(memberDto.lastname());
            setBirthday(memberDto.birthday());
            setEmail(memberDto.email());
            setGender(memberDto.gender());
        }};
        memberService.save(member);
    }

    @ExceptionHandler(RecordNotValidException.class)
    public ResponseEntity<String> handle(RecordNotValidException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
