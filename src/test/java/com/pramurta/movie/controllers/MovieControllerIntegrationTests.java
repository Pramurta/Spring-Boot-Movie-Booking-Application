package com.pramurta.movie.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pramurta.movie.domain.entities.Movie;
import com.pramurta.movie.domain.enums.UserRole;
import com.pramurta.movie.services.MovieService;
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
public class MovieControllerIntegrationTests {

    private final MockHttpSession mockHttpSession;
    private final MovieService movieService;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    public MovieControllerIntegrationTests(MovieService movieService, MockMvc mockMvc, ObjectMapper objectMapper) {
        this.movieService = movieService;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("username","PN1");
        mockHttpSession.setAttribute("userRoles", Lists.newArrayList(
                UserRole.CUSTOMER,UserRole.APP_ADMIN,UserRole.MOVIE_ADMIN,UserRole.THEATRE_ADMIN));
        movieService.removeAllMovies();
    }
    @Test
    public void testThatMovieCanBeCreated() throws Exception {
        Movie movie = TestDataUtil.createTestMovie();
        String movieStr = objectMapper.writeValueAsString(movie);
        mockMvc.perform(MockMvcRequestBuilders.post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(movieStr)
                .session(mockHttpSession)
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.movieName").value(movie.getMovieName()));
        movieService.removeMovie(movie.getMovieName());
    }

    @Test
    public void testThatMovieCanBeRemoved() throws Exception {
        Movie movie = TestDataUtil.createTestMovie();
        movieService.createMovie(movie);
        mockMvc.perform(MockMvcRequestBuilders.delete("/movies/"+movie.getMovieName())
                .session(mockHttpSession)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
