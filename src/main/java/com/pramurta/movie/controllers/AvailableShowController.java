package com.pramurta.movie.controllers;

import com.pramurta.movie.controllers.helpers.ControllerHelper;
import com.pramurta.movie.domain.entities.AvailableShow;
import com.pramurta.movie.domain.dtos.AvailableShowDto;
import com.pramurta.movie.helpers.ResponseHelper;
import com.pramurta.movie.mappers.AvailableShowMapper;
import com.pramurta.movie.services.AvailableShowService;
import jakarta.servlet.http.HttpSession;
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
            @RequestParam(name = "toTime", required = false) String toTime,
            HttpSession httpSession
    ) {
        try {
            if(!ControllerHelper.isUserLoggedIn(httpSession)) {
                return new ResponseEntity<>(ControllerHelper.USER_NOT_LOGGED_IN_MESSAGE,HttpStatus.UNAUTHORIZED);
            }
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
            return new ResponseEntity<>(ResponseHelper.constructSuccessResponse(availableShowDtos), HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(ResponseHelper.constructFailureResponse(e.getMessage())
                    ,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/availableShows")
    public ResponseEntity<?> createAShow(@RequestBody AvailableShowDto availableShowDto, HttpSession httpSession) {
        try {
            if(!ControllerHelper.isUserLoggedIn(httpSession)) {
                return new ResponseEntity<>("Please login first to create a show",HttpStatus.UNAUTHORIZED);
            }
            if(!ControllerHelper.isUserTheatreAdmin(httpSession)) {
                return new ResponseEntity<>("Only theatre admins are allowed to create a show",HttpStatus.UNAUTHORIZED);
            }
            AvailableShow availableShow = availableShowMapper.mapToEntity(availableShowDto);
            AvailableShowDto availableShowDtoCreated = availableShowMapper.mapToDto(availableShowService.createAShow(availableShow));
            return new ResponseEntity<>(ResponseHelper.constructSuccessResponse(availableShowDtoCreated),HttpStatus.CREATED);
        } catch(Exception e) {
            return new ResponseEntity<>(ResponseHelper.constructFailureResponse(e.getMessage())
                    ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/availableShows/{showID}")
    public ResponseEntity<?> removeAShow(@PathVariable("showID") String showID, HttpSession httpSession) {
        try {
            if(!ControllerHelper.isUserLoggedIn(httpSession)) {
                return new ResponseEntity<>("Please login first to remove a show",HttpStatus.UNAUTHORIZED);
            }
            if(!ControllerHelper.isUserTheatreAdmin(httpSession)) {
                return new ResponseEntity<>("Only theatre admins are allowed to create a show",HttpStatus.UNAUTHORIZED);
            }
            availableShowService.removeAShow(showID);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseHelper.constructFailureResponse(e.getMessage())
                    ,HttpStatus.BAD_REQUEST);
        }
    }




}
