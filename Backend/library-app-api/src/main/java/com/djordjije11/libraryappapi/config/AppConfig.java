package com.djordjije11.libraryappapi.config;

import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import com.google.common.base.CaseFormat;

@Configuration
public class AppConfig {

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
