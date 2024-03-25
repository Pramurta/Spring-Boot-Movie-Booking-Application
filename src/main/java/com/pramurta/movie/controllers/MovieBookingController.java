package com.pramurta.movie.controllers;

import com.pramurta.movie.controllers.helpers.ControllerHelper;
import com.pramurta.movie.domain.entities.MovieBooking;
import com.pramurta.movie.helpers.ResponseHelper;
import com.pramurta.movie.services.AvailableShowService;
import com.pramurta.movie.services.MovieBookingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MovieBookingController {
    private final MovieBookingService movieBookingService;
    private final AvailableShowService availableShowService;

    public MovieBookingController(MovieBookingService movieBookingService, AvailableShowService availableShowService) {
        this.movieBookingService = movieBookingService;
        this.availableShowService = availableShowService;
    }

    @PostMapping(path = "/movieBookings")
    public ResponseEntity<?> createMovieBooking(@RequestBody MovieBooking movieBookingRequest, HttpSession httpSession) {
        try {
            if(!ControllerHelper.isUserLoggedIn(httpSession)) {
                return new ResponseEntity<>("Please login first to book your show!",HttpStatus.UNAUTHORIZED);
            }
            MovieBooking movieBooking = movieBookingService.createMovieBooking(movieBookingRequest);
            availableShowService.setSeatsToBeNotAvailableAfterMovieBookingIsSuccessful(movieBookingRequest);
            return new ResponseEntity<>(ResponseHelper.constructSuccessResponse(movieBooking),HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseHelper.constructFailureResponse(e.getMessage())
                    ,HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/movieBookings/{movieBookingId}")
    public ResponseEntity<?> cancelMovieBooking(@PathVariable("movieBookingId") String movieBookingId, HttpSession httpSession) {
        try {
            if(!ControllerHelper.isUserLoggedIn(httpSession)) {
                return new ResponseEntity<>("Please login first to cancel your show!",HttpStatus.UNAUTHORIZED);
            }
            availableShowService.setSeatsToBeAvailableBeforeMovieBookingIsCancelled(movieBookingId);
            movieBookingService.cancelBooking(movieBookingId);
            return new ResponseEntity<>(String.format("Movie booking with ID: %s successfully cancelled",movieBookingId),HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseHelper.constructFailureResponse(e.getMessage())
                    ,HttpStatus.BAD_REQUEST);
        }
    }
}
