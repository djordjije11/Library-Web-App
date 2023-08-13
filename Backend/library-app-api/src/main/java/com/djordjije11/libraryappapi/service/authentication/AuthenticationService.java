package com.djordjije11.libraryappapi.service.authentication;

import com.djordjije11.libraryappapi.dto.authentication.LoginDto;
import com.djordjije11.libraryappapi.dto.authentication.JwtTokenDto;
import com.djordjije11.libraryappapi.exception.RecordNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AuthenticationService {
//    JwtTokenDto register(LoginDto request);

    /**
     * Logins the user to the library system if the username and the password are valid and returns the valid JWT token.
     * Throws:
     * UsernameNotFoundException when the user profile with the input username does not exist in the library system's database,
     * RecordNotFoundException when the employee with the specified user profile does not exist in the library system's database.
     * @param request consists of the username and the password.
     * @return valid JWT token.
     * @throws UsernameNotFoundException when the user profile with the input username does not exist in the library system's database.
     * @throws RecordNotFoundException when the employee with the specified user profile does not exist in the library system's database.
     */
    JwtTokenDto login(LoginDto request);
}
