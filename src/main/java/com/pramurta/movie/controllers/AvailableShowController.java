package com.pramurta.movie.controllers;

import com.pramurta.movie.domain.AvailableShow;
import com.pramurta.movie.services.AvailableShowService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AvailableShowController {
    private final AvailableShowService availableShowService;

    public AvailableShowController(AvailableShowService availableShowService) {
        this.availableShowService = availableShowService;
    }

    @GetMapping(path = "/availableShows")
    public ResponseEntity<?> getAvailableShows(
            @RequestParam(name = "movieName", required = false) String movieName,
            @RequestParam(name = "theatreName", required = false) String theatreName,
            @RequestParam(name = "fromTime", required = false) String fromTime,
            @RequestParam(name = "toTime", required = false) String toTime
    ) {
        if(movieName!=null && theatreName==null && fromTime==null && toTime==null) {
            try {
                return new ResponseEntity<>(availableShowService.getShowsByMovieName(movieName), HttpStatus.OK);
            }
            catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
            }
        }
        else if(movieName==null && theatreName!=null && fromTime==null && toTime==null) {
            try {
                return new ResponseEntity<>(availableShowService.getShowsByTheatreName(theatreName), HttpStatus.OK);
            }
            catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
            }
        }
        else if(movieName==null && theatreName==null && fromTime!=null && toTime!=null) {
            try {
                return new ResponseEntity<>(availableShowService.getShowsWithinDateTimeRange(fromTime, toTime), HttpStatus.OK);
            }
            catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
            }
        }
        else if(movieName!=null && theatreName==null && fromTime!=null && toTime!=null) {
            try {
                return new ResponseEntity<>(availableShowService.getShowsByMovieNameAndDateTimeRange(movieName, fromTime, toTime), HttpStatus.OK);
            }
            catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
            }
        }
        else if(movieName==null && theatreName!=null && fromTime!=null && toTime!=null) {
            try {
                return new ResponseEntity<>(availableShowService.getShowsByTheatreNameAndDateTimeRange(theatreName, fromTime, toTime), HttpStatus.OK);
            }
            catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
            }
        }
        else {
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.OK);
        }
    }


}
