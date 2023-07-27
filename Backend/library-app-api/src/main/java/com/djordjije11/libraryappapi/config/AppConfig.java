package com.djordjije11.libraryappapi.config;

import com.djordjije11.libraryappapi.repository.UserProfileRepository;
import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import com.google.common.base.CaseFormat;

@Configuration
public class AppConfig {
    private final UserProfileRepository userProfileRepository;

    @Value("${client.url}")
    private String clientUrl;
    @Value("${client.port}")
    private String clientPort;

    public AppConfig(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userProfileRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with username: %s does not exist.", username)));
    }

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        String allowedOrigin = String.format("%s:%s", clientUrl, clientPort);
        config.setAllowedOrigins(Collections.singletonList(allowedOrigin));
        config.setAllowedHeaders(Arrays.asList(
                HttpHeaders.ORIGIN,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.ACCEPT,
                HttpHeaders.AUTHORIZATION
        ));
        config.setAllowedMethods(Arrays.asList(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.OPTIONS.name(),
                HttpMethod.DELETE.name()
        ));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public Filter snakeConverter() {
        return new OncePerRequestFilter() {

            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                    throws ServletException, IOException {
                final Map<String, String[]> formattedParams = new ConcurrentHashMap<>();

                for (String param : request.getParameterMap().keySet()) {
                    String formattedParam = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, param);
                    formattedParams.put(formattedParam, request.getParameterValues(param));
                }

                filterChain.doFilter(new HttpServletRequestWrapper(request) {
                    @Override
                    public String getParameter(String name) {
                        return formattedParams.containsKey(name) ? formattedParams.get(name)[0] : null;
                    }

                    @Override
                    public Enumeration<String> getParameterNames() {
                        return Collections.enumeration(formattedParams.keySet());
                    }

                    @Override
                    public String[] getParameterValues(String name) {
                        return formattedParams.get(name);
                    }

                    @Override
                    public Map<String, String[]> getParameterMap() {
                        return formattedParams;
                    }
                },
                        response);
            }
        };
    }
}
