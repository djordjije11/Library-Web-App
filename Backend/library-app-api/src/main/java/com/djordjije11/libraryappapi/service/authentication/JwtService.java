package com.djordjije11.libraryappapi.service.authentication;

import com.djordjije11.libraryappapi.config.authentication.AuthClaimsHolder;
import com.djordjije11.libraryappapi.config.authentication.claim.BuildingClaim;
import com.djordjije11.libraryappapi.config.authentication.claim.EmployeeClaim;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public interface JwtService {
    String AUTH_HEADER_START_TEXT = "Bearer ";

    String extractUsername(String token);
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);
    String generateToken(UserDetails userDetails);
    String generateToken(EmployeeClaim employeeClaim, BuildingClaim buildingClaim, UserDetails userDetails);
    EmployeeClaim extractEmployeeClaim(String token);
    BuildingClaim extractBuildingClaim(String token);
    void setUpAuthClaimsHolder(AuthClaimsHolder authClaimsHolder, String token);
    boolean isTokenValid(String token, UserDetails userDetails);
    Date extractExpiration(String token);
    default Optional<String> getTokenFromHeader(String authHeader){
        if (authHeader == null || authHeader.startsWith(AUTH_HEADER_START_TEXT) == false) {
            return Optional.empty();
        }
        return Optional.of(authHeader.substring(AUTH_HEADER_START_TEXT.length()));
    }
}
