package com.pramurta.movie.controllers;

import com.pramurta.movie.controllers.helpers.ControllerHelper;
import com.pramurta.movie.domain.entities.Movie;
import com.pramurta.movie.helpers.ResponseHelper;
import com.pramurta.movie.services.MovieService;
import jakarta.servlet.http.HttpSession;
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
    public ResponseEntity<?> createMovie(@RequestBody Movie moviePayload, HttpSession httpSession) {
        try {
            if(!ControllerHelper.isUserLoggedIn(httpSession)) {
                return new ResponseEntity<>(ResponseHelper.constructFailureResponse("Please login first to create a movie"),HttpStatus.UNAUTHORIZED);
            }
            if(!ControllerHelper.isUserMovieAdmin(httpSession)) {
                return new ResponseEntity<>(ResponseHelper.constructFailureResponse("Only movie admins are allowed to create a movie"),HttpStatus.UNAUTHORIZED);
            }
            Movie movie = movieService.createMovie(moviePayload);
            return new ResponseEntity<>(ResponseHelper.constructSuccessResponse(movie), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseHelper.constructFailureResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping(path = "/movies/{movieName}")
    public ResponseEntity<?> removeMovie(@PathVariable("movieName") String movieName, HttpSession httpSession) {
        try {
            if(!ControllerHelper.isUserLoggedIn(httpSession)) {
                return new ResponseEntity<>(ResponseHelper.constructFailureResponse("Please login first to create a movie"),HttpStatus.UNAUTHORIZED);
            }
            if(!ControllerHelper.isUserMovieAdmin(httpSession)) {
                return new ResponseEntity<>(ResponseHelper.constructFailureResponse("Only movie admins are allowed to create a movie"),HttpStatus.UNAUTHORIZED);
            }
            movieService.removeMovie(movieName);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseHelper.constructFailureResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
