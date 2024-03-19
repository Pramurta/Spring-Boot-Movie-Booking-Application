package com.pramurta.movie.controllers;

import com.pramurta.movie.domain.entities.Movie;
import com.pramurta.movie.helpers.ResponseHelper;
import com.pramurta.movie.services.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping(path = "/movies")
    public ResponseEntity<?> createMovie(@RequestBody Movie moviePayload) {
        try {
            Movie movie = movieService.createMovie(moviePayload);
            return new ResponseEntity<>(ResponseHelper.constructSuccessfulAPIResponse(movie), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseHelper.constructFailedAPIResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping(path = "/movies/{movieName}")
    public ResponseEntity<?> removeMovie(@PathVariable("movieName") String movieName) {
        try {
            movieService.removeMovie(movieName);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseHelper.constructFailedAPIResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
