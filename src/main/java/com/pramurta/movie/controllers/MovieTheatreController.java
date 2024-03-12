package com.pramurta.movie.controllers;

import com.pramurta.movie.domain.entities.MovieTheatre;
import com.pramurta.movie.services.MovieTheatreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MovieTheatreController {
    private final MovieTheatreService movieTheatreService;

    public MovieTheatreController(MovieTheatreService movieTheatreService) {
        this.movieTheatreService = movieTheatreService;
    }

    @PostMapping(path = "/movieTheatres")
    public ResponseEntity<?> createMovieTheatre(@RequestBody MovieTheatre movieTheatrePayload) {
        try {
            return new ResponseEntity<>(movieTheatreService.createMovieTheatre(movieTheatrePayload), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping(path = "/movieTheatres/{movieTheatreName}")
    public ResponseEntity<?> removeMovieTheatre(@PathVariable("movieTheatreName") String movieTheatreName) {
        try {
            movieTheatreService.removeMovieTheatre(movieTheatreName);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
