package com.pramurta.movie.utils;

import com.pramurta.movie.domain.Person;

public final class TestDataUtil {

    public static Person createTestPerson() {
        return Person.builder()
                .passportNumber("PN1")
                .name("Person1")
                .cardNumber("Card1")
                .build();
    }
}
