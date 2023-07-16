package com.djordjije11.libraryappapi.seed;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Locale;

@Configuration
public class SeederConfig {
    private static final Locale SEED_LOCALE = Locale.US;

    @Bean
    public Faker getFaker(){
        return new Faker(SEED_LOCALE);
    }
}
