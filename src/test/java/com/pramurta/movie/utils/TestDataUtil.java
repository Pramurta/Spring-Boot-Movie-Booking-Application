package com.pramurta.movie.utils;

import com.pramurta.movie.domain.dtos.UpdatePersonDto;
import com.pramurta.movie.domain.entities.AvailableShow;
import com.pramurta.movie.domain.entities.Movie;
import com.pramurta.movie.domain.entities.Person;
import com.pramurta.movie.domain.enums.UserRole;
import com.pramurta.movie.domain.models.MovieSeat;
import org.assertj.core.util.Lists;

import java.time.LocalDateTime;

public final class TestDataUtil {

    public static Person createTestPerson() {
        return Person.builder()
                .passportNumber("PN1")
                .name("Person1")
                .userRole(UserRole.CUSTOMER)
                .password("123456")
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
                .seats(Lists.newArrayList(MovieSeat.builder()
                                .seatNumber("A1")
                                .isAvailable(true)
                        .build()))
                .build();
    }
}
