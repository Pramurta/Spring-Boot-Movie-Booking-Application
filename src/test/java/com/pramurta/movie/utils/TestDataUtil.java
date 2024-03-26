package com.pramurta.movie.utils;

import com.pramurta.movie.domain.entities.AvailableShow;
import com.pramurta.movie.domain.entities.Movie;
import com.pramurta.movie.domain.entities.MovieTheatre;
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
                .userRoles(Lists.newArrayList(UserRole.CUSTOMER,UserRole.APP_ADMIN,UserRole.MOVIE_ADMIN,UserRole.THEATRE_ADMIN))
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

    public static MovieTheatre createTestMovieTheatre() {
        return MovieTheatre.builder()
                .movieTheatreName("GV Vivo City")
                .location("1 HarbourFront Walk, #02-30 VivoCity, Singapore 098585")
                .hallNumbers(Lists.newArrayList(1,2,3,4,5,6))
                .build();
    }

    public static AvailableShow createShow() {
        return AvailableShow.builder()
                .theatreName("GV Vivo City")
                .movieName("Mission Impossible Part 1")
                .fromTime(LocalDateTime.now().plusHours(1))
                .toTime(LocalDateTime.now().plusHours(2))
                .hallNumber(1)
                .seats(Lists.newArrayList(MovieSeat.builder()
                                .seatNumber("A1")
                                .isAvailable(true)
                        .build()))
                .build();
    }
}
