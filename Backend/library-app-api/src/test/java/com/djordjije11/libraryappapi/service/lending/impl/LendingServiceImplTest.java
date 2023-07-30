package com.djordjije11.libraryappapi.service.lending.impl;

import com.djordjije11.libraryappapi.service.lending.LendingServiceTest;
import org.junit.jupiter.api.BeforeEach;

public class LendingServiceImplTest extends LendingServiceTest {

    @BeforeEach
    public void init(){
        lendingService = new LendingServiceImpl(lendingRepository, buildingRepository, memberRepository, bookCopyRepository);
    }
}