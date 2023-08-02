package com.djordjije11.libraryappapi.controller;

import com.djordjije11.libraryappapi.dto.authentication.LoginDto;
import com.djordjije11.libraryappapi.dto.authentication.JwtTokenDto;
import com.djordjije11.libraryappapi.service.authentication.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDto> login(@RequestBody @Valid LoginDto loginDto){
        return ResponseEntity.ok(authenticationService.login(loginDto));
    }

    // TODO: 7/27/2023 NEEDS TO BE FIXED, IF IT WILL BE EVEN USED
    @PostMapping("/register")
    public ResponseEntity<JwtTokenDto> register(@RequestBody @Valid LoginDto loginDto){
        return ResponseEntity.ok(authenticationService.register(loginDto));
    }
}
