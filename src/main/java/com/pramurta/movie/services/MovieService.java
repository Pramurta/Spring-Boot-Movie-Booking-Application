package com.pramurta.movie.services;

import com.pramurta.movie.domain.Movie;
import com.pramurta.movie.domain.Person;
import com.pramurta.movie.repositories.MovieRepository;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private static final String MOVIE_ALREADY_EXISTS_TEMPLATE = "Movie with name %s already exists";

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie createMovie(Movie movie) throws Exception{
        if(movieRepository.existsById(movie.getMovieName())) {
            throw new Exception(String.format(MOVIE_ALREADY_EXISTS_TEMPLATE,movie.getMovieName()));
        }
        return movieRepository.save(movie);
    }

    public void removeMovie(String movieName) {
        movieRepository.deleteById(movieName);
    }


}
