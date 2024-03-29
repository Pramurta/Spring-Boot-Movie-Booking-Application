package com.pramurta.movie.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pramurta.movie.domain.dtos.AvailableShowDto;
import com.pramurta.movie.domain.entities.AvailableShow;
import com.pramurta.movie.domain.entities.Movie;
import com.pramurta.movie.domain.entities.MovieTheatre;
import com.pramurta.movie.domain.enums.UserRole;
import com.pramurta.movie.domain.models.MovieSeat;
import com.pramurta.movie.mappers.AvailableShowMapper;
import com.pramurta.movie.services.AvailableShowService;
import com.pramurta.movie.services.MovieService;
import com.pramurta.movie.services.MovieTheatreService;
import com.pramurta.movie.utils.TestDataUtil;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;



@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class AvailableShowIntegrationTests {
    private final MockMvc mockMvc;
    private final AvailableShowService availableShowService;
    private final MovieService movieService;

    private final MovieTheatreService movieTheatreService;
    private final MockHttpSession mockHttpSession;

    private final ObjectMapper objectMapper;

    @Autowired
    public AvailableShowIntegrationTests(MockMvc mockMvc, AvailableShowService availableShowService, MovieService movieService, MovieTheatreService movieTheatreService, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.availableShowService = availableShowService;
        this.movieService = movieService;
        this.movieTheatreService = movieTheatreService;
        this.objectMapper = objectMapper;
        this.mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("username","PN1");
        mockHttpSession.setAttribute("userRoles", Lists.newArrayList(
                UserRole.CUSTOMER,UserRole.APP_ADMIN,UserRole.MOVIE_ADMIN,UserRole.THEATRE_ADMIN));
        availableShowService.removeAllShows();
    }

    @Test
    public void testThatShowCanBeCreated() throws Exception{
        Movie movie = TestDataUtil.createTestMovie();
        movieService.createMovie(movie);

        MovieTheatre movieTheatre = TestDataUtil.createTestMovieTheatre();
        movieTheatreService.createMovieTheatre(movieTheatre);

        AvailableShowDto availableShowDto = AvailableShowDto.builder()
                .fromTime("2024-03-28 14:00")
                .toTime("2024-03-28 15:00")
                .movieName(movie.getMovieName())
                .theatreName(movieTheatre.getMovieTheatreName())
                .hallNumber(movieTheatre.getHallNumbers().getFirst())
                .seats(Lists.newArrayList(
                        MovieSeat.builder()
                                .seatNumber("A1")
                                .isAvailable(true)
                                .build()
                ))
                .perTicketPrice(20)
                .build();
        String availableShowDtoStr = objectMapper.writeValueAsString(availableShowDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/availableShows")
                .contentType(MediaType.APPLICATION_JSON)
                .content(availableShowDtoStr)
                .session(mockHttpSession)
        )
                .andExpect(MockMvcResultMatchers.status().isCreated());
        AvailableShow availableShow = availableShowService.getShowsByMovieName(movie.getMovieName()).get(0);
        availableShowService.removeAShow(availableShow.getId().toString());
        movieService.removeMovie(movie.getMovieName());
        movieTheatreService.removeMovieTheatre(movieTheatre.getMovieTheatreName());
    }

    @Test
    public void testThatShowCanBeRemoved() throws Exception {
        Movie movie = TestDataUtil.createTestMovie();
        movieService.createMovie(movie);

        MovieTheatre movieTheatre = TestDataUtil.createTestMovieTheatre();
        movieTheatreService.createMovieTheatre(movieTheatre);

        AvailableShow availableShow = TestDataUtil.createShow();
        availableShowService.createAShow(availableShow);

        mockMvc.perform(MockMvcRequestBuilders.delete("/availableShows/"+availableShow.getId().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .session(mockHttpSession)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());

        movieService.removeMovie(movie.getMovieName());
        movieTheatreService.removeMovieTheatre(movieTheatre.getMovieTheatreName());

    }
}
