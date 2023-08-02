package com.djordjije11.libraryappapi.config.authentication;

import com.djordjije11.libraryappapi.service.authentication.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final AuthClaimsHolder authClaimsHolder;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService, AuthClaimsHolder authClaimsHolder) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.authClaimsHolder = authClaimsHolder;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String jwtToken;
        final String username;

        var maybeJwtToken = jwtService.getTokenFromHeader(authHeader);
        if (maybeJwtToken.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        jwtToken = maybeJwtToken.get();
        username = jwtService.extractUsername(jwtToken);

        if (username == null || SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (jwtService.isTokenValid(jwtToken, userDetails) == false) {
            filterChain.doFilter(request, response);
            return;
        }

        var authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        jwtService.setUpAuthClaimsHolder(authClaimsHolder, jwtToken);

        filterChain.doFilter(request, response);
    }
}
