package com.djordjije11.libraryappapi.seed;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Locale;

@Configuration
public class SeederConfig {

    @Value("${seed.active}")
    public boolean ACTIVE;
    @Value("${seed.count.building}")
    public int BUILDINGS_COUNT;
    @Value("${seed.count.city}")
    public int CITIES_COUNT;
    @Value("${seed.count.employee}")
    public int EMPLOYEES_COUNT;
    @Value("${seed.count.publisher}")
    public int PUBLISHER_COUNT;
    @Value("${seed.count.book}")
    public int BOOK_COUNT;
    @Value("${seed.count.book-copy-max-per-book}")
    public int BOOK_COPY_MAX_PER_BOOK_COUNT;
    @Value("${seed.count.member}")
    public int MEMBER_COUNT;
    @Value("${seed.count.lending}")
    public int LENDING_COUNT;

    private static final Locale SEED_LOCALE = Locale.US;
    @Bean
    public Faker getFaker(){
        return new Faker(SEED_LOCALE);
    }
}
