package com.djordjije11.libraryappapi;

import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    @Value("${client.url}")
    private String clientUrl;
    @Value("${client.port}")
    private String clientPort;

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Collections.singletonList(String.format("%s:%s", clientUrl, clientPort)));
        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
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
                }, response);
            }
        };
    }
}
