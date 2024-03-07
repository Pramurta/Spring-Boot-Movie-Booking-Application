package com.pramurta.movie.utils;

import com.pramurta.movie.domain.AvailableShow;
import com.pramurta.movie.domain.Movie;
import com.pramurta.movie.domain.Person;
import org.assertj.core.util.Lists;

import java.time.LocalDateTime;

public final class TestDataUtil {

    public static Person createTestPerson() {
        return Person.builder()
                .passportNumber("PN1")
                .name("Person1")
                .cardNumber("Card1")
                .build();
    }

    public static Movie createTestMovie() {
        return Movie.builder()
                .movieName("Mission Impossible Part 1")
                .genre("Action")
                .actors(Lists.newArrayList("Tom Cruise"))
                .director("Brian De Palma")
                .build();
    }

    public static AvailableShow createShow() {
        return AvailableShow.builder()
                .theatreName("GV Great World")
                .movieName("Mission Impossible Part 2")
                .fromTime(LocalDateTime.now())
                .toTime(LocalDateTime.now().plusHours(2))
                .hallNumber(1)
                .seatsAvailable(Lists.newArrayList("A1","A2","B1","B2"))
                .build();
    }
}
