package com.djordjije11.libraryappapi.config.authentication;

import com.djordjije11.libraryappapi.service.authentication.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
    private final EmployeeClaimHolder employeeClaimHolder;
    private final JwtService jwtService;

    public AuthorizationInterceptor(EmployeeClaimHolder employeeClaimHolder, JwtService jwtService) {
        this.employeeClaimHolder = employeeClaimHolder;
        this.jwtService = jwtService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        var maybeJwtToken = jwtService.getTokenFromHeader(authorization);
        if(maybeJwtToken.isEmpty()){
            return true;
        }
        employeeClaimHolder.setEmployeeClaim(jwtService.extractEmployeeClaim(maybeJwtToken.get()));
        return true;
    }
}
