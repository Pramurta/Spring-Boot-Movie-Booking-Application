package com.pramurta.movie.services;

import com.pramurta.movie.domain.MovieTheatre;
import com.pramurta.movie.repositories.MovieTheatreRepository;
import org.springframework.stereotype.Service;

@Service
public class MovieTheatreService {
    private final MovieTheatreRepository movieTheatreRepository;
    private static final String MOVIE_THEATRE_ALREADY_EXISTS_TEMPLATE = "Movie theatre with name %s already exists";

    public MovieTheatreService(MovieTheatreRepository movieTheatreRepository) {
        this.movieTheatreRepository = movieTheatreRepository;
    }

    public MovieTheatre createMovieTheatre(MovieTheatre movieTheatrePayload) throws Exception{
        if(movieTheatreRepository.existsById(movieTheatrePayload.getMovieTheatreName())) {
            throw new Exception(String.format(MOVIE_THEATRE_ALREADY_EXISTS_TEMPLATE,movieTheatrePayload.getMovieTheatreName()));
        }
        return movieTheatreRepository.save(movieTheatrePayload);
    }

    public void removeMovieTheatre(String movieTheatreName) {
        movieTheatreRepository.deleteById(movieTheatreName);
    }
}
