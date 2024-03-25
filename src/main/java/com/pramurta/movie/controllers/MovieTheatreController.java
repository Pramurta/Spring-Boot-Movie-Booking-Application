package com.pramurta.movie.controllers;

import com.pramurta.movie.controllers.helpers.ControllerHelper;
import com.pramurta.movie.domain.entities.MovieTheatre;
import com.pramurta.movie.helpers.ResponseHelper;
import com.pramurta.movie.services.MovieTheatreService;
import jakarta.servlet.http.HttpSession;
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
    public ResponseEntity<?> createMovieTheatre(@RequestBody MovieTheatre movieTheatrePayload, HttpSession httpSession) {
        try {
            if(!ControllerHelper.isUserLoggedIn(httpSession)) {
                return new ResponseEntity<>(ResponseHelper.constructFailureResponse("Please login first to create a movie theatre") ,HttpStatus.UNAUTHORIZED);
            }
            if(!ControllerHelper.isUserAppAdmin(httpSession)) {
                return new ResponseEntity<>(ResponseHelper.constructFailureResponse("Only app admins are allowed to create a movie theatre"),HttpStatus.UNAUTHORIZED);
            }
            MovieTheatre movieTheatre = movieTheatreService.createMovieTheatre(movieTheatrePayload);
            return new ResponseEntity<>(ResponseHelper.constructSuccessResponse(movieTheatre), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseHelper.constructFailureResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping(path = "/movieTheatres/{movieTheatreName}")
    public ResponseEntity<?> removeMovieTheatre(@PathVariable("movieTheatreName") String movieTheatreName, HttpSession httpSession) {
        try {
            if(!ControllerHelper.isUserLoggedIn(httpSession)) {
                return new ResponseEntity<>(ResponseHelper.constructFailureResponse("Please login first to remove a movie theatre"),HttpStatus.UNAUTHORIZED);
            }
            if(!ControllerHelper.isUserAppAdmin(httpSession)) {
                return new ResponseEntity<>(ResponseHelper.constructFailureResponse("Only app admins are allowed to create a movie theatre"),HttpStatus.UNAUTHORIZED);
            }
            movieTheatreService.removeMovieTheatre(movieTheatreName);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseHelper.constructFailureResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
