package com.djordjije11.libraryappapi.service.authentication.impl;

import com.djordjije11.libraryappapi.controller.request.authentication.AuthenticationRequest;
import com.djordjije11.libraryappapi.controller.response.authentication.AuthenticationResponse;
import com.djordjije11.libraryappapi.config.authentication.EmployeeClaim;
import com.djordjije11.libraryappapi.model.UserProfile;
import com.djordjije11.libraryappapi.model.UserRole;
import com.djordjije11.libraryappapi.repository.EmployeeRepository;
import com.djordjije11.libraryappapi.repository.UserProfileRepository;
import com.djordjije11.libraryappapi.service.authentication.AuthenticationService;
import com.djordjije11.libraryappapi.service.authentication.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserProfileRepository userProfileRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceImpl(UserProfileRepository userProfileRepository, EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userProfileRepository = userProfileRepository;
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    private String generateToken(UserProfile userProfile){
        var employee = employeeRepository.findByUserProfile_Id(userProfile.getId())
                .orElseThrow(() -> new UsernameNotFoundException(String.format("An employee with username: %s does not exist.", userProfile.getUsername())));
        var employeeClaim = new EmployeeClaim(
                employee.getId(),
                employee.getIdCardNumber(),
                employee.getFirstname(),
                employee.getLastname(),
                employee.getEmail(),
                employee.getBuilding().getId(),
                employee.getUserProfile().getId()
        );
        return jwtService.generateToken(employeeClaim, userProfile);
    }

    // TODO: 7/27/2023 NEEDS TO BE FIXED, IF IT WILL BE EVEN USED
    @Override
    public AuthenticationResponse register(AuthenticationRequest request) {
        var userProfile = new UserProfile(request.username(), passwordEncoder.encode(request.password()), UserRole.EMPLOYEE);
        userProfileRepository.save(userProfile);
        String jwtToken = generateToken(userProfile);
        return new AuthenticationResponse(jwtToken);
    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        var userProfile = userProfileRepository.findByUsername(request.username())
                .orElseThrow(() -> new UsernameNotFoundException(String.format("A user with username: %s does not exist.", request.username())));
        String jwtToken = generateToken(userProfile);
        return new AuthenticationResponse(jwtToken);
    }
}
