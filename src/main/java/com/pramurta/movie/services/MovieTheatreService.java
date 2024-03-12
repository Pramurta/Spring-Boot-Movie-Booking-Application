package com.pramurta.movie.services;

import com.pramurta.movie.domain.entities.AvailableShow;
import com.pramurta.movie.domain.entities.MovieTheatre;
import com.pramurta.movie.repositories.AvailableShowRepository;
import com.pramurta.movie.repositories.MovieTheatreRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieTheatreService {
    private final MovieTheatreRepository movieTheatreRepository;
    private final AvailableShowRepository availableShowRepository;
    private static final String MOVIE_THEATRE_ALREADY_EXISTS_TEMPLATE = "Movie theatre with name %s already exists";

    public MovieTheatreService(MovieTheatreRepository movieTheatreRepository, AvailableShowRepository availableShowRepository) {
        this.movieTheatreRepository = movieTheatreRepository;
        this.availableShowRepository = availableShowRepository;
    }

    public MovieTheatre createMovieTheatre(MovieTheatre movieTheatrePayload) throws Exception{
        if(movieTheatreRepository.existsById(movieTheatrePayload.getMovieTheatreName())) {
            throw new Exception(String.format(MOVIE_THEATRE_ALREADY_EXISTS_TEMPLATE,movieTheatrePayload.getMovieTheatreName()));
        }
        return movieTheatreRepository.save(movieTheatrePayload);
    }

    public void removeMovieTheatre(String movieTheatreName) throws Exception{
        boolean movieTheatreExists = movieTheatreRepository.existsById(movieTheatreName);
        if(movieTheatreExists) {
            Optional<AvailableShow> availableShow = availableShowRepository.findShowsByTheatreName(movieTheatreName).stream().findFirst();
            if(availableShow.isPresent()) {
                throw new Exception(String.format("There's a movie: %s playing in the movie theatre: %s. Hence, can't remove the theatre.",availableShow.get().getMovieName(),
                        availableShow.get().getTheatreName()));
            }
        }
        movieTheatreRepository.deleteById(movieTheatreName);
    }
}
