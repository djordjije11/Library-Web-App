package com.djordjije11.libraryappapi.service.authentication;

import com.djordjije11.libraryappapi.dto.authentication.LoginDto;
import com.djordjije11.libraryappapi.dto.authentication.JwtTokenDto;

public interface AuthenticationService {
    JwtTokenDto register(LoginDto request);
    JwtTokenDto login(LoginDto request);
}
