package com.djordjije11.libraryappapi.config;

import com.djordjije11.libraryappapi.controller.response.ResponseHeadersFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class CorsFilterConfig {
    @Value("${client.url}")
    private String clientUrl;
    @Value("${client.port}")
    private String clientPort;

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
        config.setExposedHeaders(Arrays.asList(
                HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,
                HttpHeaders.CONTENT_TYPE,
                ResponseHeadersFactory.PAGINATION_TOTAL_PAGES,
                ResponseHeadersFactory.PAGINATION_TOTAL_ENTRIES
        ));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
