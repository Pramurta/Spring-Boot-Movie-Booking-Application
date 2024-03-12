package com.pramurta.movie.controllers;

import com.pramurta.movie.domain.entities.AvailableShow;
import com.pramurta.movie.domain.dtos.AvailableShowDto;
import com.pramurta.movie.mappers.AvailableShowMapper;
import com.pramurta.movie.services.AvailableShowService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AvailableShowController {
    private final AvailableShowService availableShowService;

    private final AvailableShowMapper availableShowMapper;

    public AvailableShowController(AvailableShowService availableShowService, AvailableShowMapper availableShowMapper) {
        this.availableShowService = availableShowService;
        this.availableShowMapper = availableShowMapper;
    }

    @GetMapping(path = "/availableShows")
    public ResponseEntity<?> getAvailableShows(
            @RequestParam(name = "movieName", required = false) String movieName,
            @RequestParam(name = "theatreName", required = false) String theatreName,
            @RequestParam(name = "fromTime", required = false) String fromTime,
            @RequestParam(name = "toTime", required = false) String toTime
    ) {
        try {
            List<AvailableShow> availableShows;
            if(movieName!=null && theatreName==null && fromTime==null && toTime==null) {
                availableShows = availableShowService.getShowsByMovieName(movieName);
            }
            else if(movieName==null && theatreName!=null && fromTime==null && toTime==null) {
                availableShows = availableShowService.getShowsByTheatreName(theatreName);
            }
            else if(movieName==null && theatreName==null && fromTime!=null && toTime!=null) {
                availableShows = availableShowService.getShowsWithinDateTimeRange(fromTime, toTime);
            }
            else if(movieName!=null && theatreName==null && fromTime!=null && toTime!=null) {
                availableShows = availableShowService.getShowsByMovieNameAndDateTimeRange(movieName, fromTime, toTime);
            }
            else if(movieName==null && theatreName!=null && fromTime!=null && toTime!=null) {
                availableShows = availableShowService.getShowsByTheatreNameAndDateTimeRange(theatreName, fromTime, toTime);
            }
            else {
                availableShows = new ArrayList<>();
            }
            List<AvailableShowDto> availableShowDtos = availableShows
                    .stream()
                    .map(availableShowMapper::mapToDto)
                    .toList();
            return new ResponseEntity<>(availableShowDtos,HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/availableShows")
    public ResponseEntity<?> createAShow(@RequestBody AvailableShowDto availableShowDto) {
        try {
            AvailableShow availableShow = availableShowMapper.mapToEntity(availableShowDto);
            return new ResponseEntity<>(availableShowMapper.mapToDto(availableShowService.createAShow(availableShow)),HttpStatus.CREATED) ;
        } catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
