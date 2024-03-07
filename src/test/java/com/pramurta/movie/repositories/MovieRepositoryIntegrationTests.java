package com.pramurta.movie.repositories;

import com.pramurta.movie.domain.Movie;
import com.pramurta.movie.utils.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MovieRepositoryIntegrationTests {
    private final MovieRepository movieRepository;
    @Autowired
    public MovieRepositoryIntegrationTests(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }
    @Test
    public void testThatMovieCanBeCreatedAndRecalled() {
        Movie movie = TestDataUtil.createTestMovie();
        movieRepository.save(movie);
        Optional<Movie> result = movieRepository.findById(movie.getMovieName());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(movie);
    }

    @Test
    public void testThatMovieCanBeUpdated() {
        Movie movie = TestDataUtil.createTestMovie();
        movieRepository.save(movie);
        movie.setMovieName("Mission Impossible Part 2");
        movieRepository.save(movie);
        Optional<Movie> result = movieRepository.findById(movie.getMovieName());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(movie);
    }

    @Test
    public void testThatMovieCanBeDeleted() {
        Movie movie = TestDataUtil.createTestMovie();
        movieRepository.save(movie);
        movieRepository.deleteById(movie.getMovieName());
        Optional<Movie> result = movieRepository.findById(movie.getMovieName());
        assertThat(result).isEmpty();
    }
}
