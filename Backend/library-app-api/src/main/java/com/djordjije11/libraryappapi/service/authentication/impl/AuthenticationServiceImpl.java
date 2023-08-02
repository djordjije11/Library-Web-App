package com.djordjije11.libraryappapi.service.authentication.impl;

import com.djordjije11.libraryappapi.dto.authentication.LoginDto;
import com.djordjije11.libraryappapi.dto.authentication.JwtTokenDto;
import com.djordjije11.libraryappapi.exception.RecordNotFoundException;
import com.djordjije11.libraryappapi.mapper.building.BuildingMapper;
import com.djordjije11.libraryappapi.mapper.employee.EmployeeMapper;
import com.djordjije11.libraryappapi.model.UserProfile;
import com.djordjije11.libraryappapi.model.UserRole;
import com.djordjije11.libraryappapi.repository.EmployeeRepository;
import com.djordjije11.libraryappapi.repository.UserProfileRepository;
import com.djordjije11.libraryappapi.service.authentication.AuthenticationService;
import com.djordjije11.libraryappapi.service.authentication.JwtService;
import org.mapstruct.factory.Mappers;
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
    private final EmployeeMapper employeeMapper = Mappers.getMapper(EmployeeMapper.class);
    private final BuildingMapper buildingMapper = Mappers.getMapper(BuildingMapper.class);

    public AuthenticationServiceImpl(UserProfileRepository userProfileRepository, EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userProfileRepository = userProfileRepository;
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    private String generateToken(UserProfile userProfile){
        var employee = employeeRepository.findByUserProfile_Id(userProfile.getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("An Employee with username: %s does not exist.", userProfile.getUsername())));
        var employeeClaim = employeeMapper.mapClaim(employee);
        var buildingClaim = buildingMapper.mapClaim(employee.getBuilding());
        return jwtService.generateToken(employeeClaim, buildingClaim, userProfile);
    }

    // TODO: 7/27/2023 NEEDS TO BE FIXED, IF IT WILL BE EVEN USED
    @Override
    public JwtTokenDto register(LoginDto request) {
        var userProfile = new UserProfile(request.username(), passwordEncoder.encode(request.password()), UserRole.EMPLOYEE);
        userProfileRepository.save(userProfile);
        String jwtToken = generateToken(userProfile);
        return new JwtTokenDto(jwtToken);
    }

    @Override
    public JwtTokenDto login(LoginDto request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        var userProfile = userProfileRepository.findByUsername(request.username())
                .orElseThrow(() -> new UsernameNotFoundException(String.format("A user with username: %s does not exist.", request.username())));
        String jwtToken = generateToken(userProfile);
        return new JwtTokenDto(jwtToken);
    }
}
