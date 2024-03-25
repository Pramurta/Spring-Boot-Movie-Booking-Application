package com.pramurta.movie.services;

import com.pramurta.movie.domain.entities.AvailableShow;
import com.pramurta.movie.domain.entities.Movie;
import com.pramurta.movie.repositories.AvailableShowRepository;
import com.pramurta.movie.repositories.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final AvailableShowRepository availableShowRepository;
    private static final String MOVIE_ALREADY_EXISTS_TEMPLATE = "Movie with name %s already exists";

    public MovieService(MovieRepository movieRepository, AvailableShowRepository availableShowRepository) {
        this.movieRepository = movieRepository;
        this.availableShowRepository = availableShowRepository;
    }

    public Movie createMovie(Movie movie) throws Exception{
        if(movieRepository.existsById(movie.getMovieName())) {
            throw new Exception(String.format(MOVIE_ALREADY_EXISTS_TEMPLATE,movie.getMovieName()));
        }
        return movieRepository.save(movie);
    }

    public void removeMovie(String movieName) throws Exception{
        boolean movieExists = movieRepository.existsById(movieName);
        if(movieExists) {
            Optional<AvailableShow> availableShow = availableShowRepository.findShowsByMovieName(movieName).stream().findFirst();
            if(availableShow.isPresent()) {
                throw new Exception(String.format("There's a movie: %s playing in the movie theatre: %s. Hence, can't remove the movie.",availableShow.get().getMovieName(),
                        availableShow.get().getTheatreName()));
            }
            movieRepository.deleteById(movieName);
        }
        else {
            throw new Exception(String.format("Movie: [%s] doesn't exist",movieName));
        }
    }

    public void removeAllMovies() {
        movieRepository.deleteAll();
    }
}
