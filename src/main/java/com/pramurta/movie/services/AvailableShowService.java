package com.pramurta.movie.services;

import com.pramurta.movie.domain.AvailableShow;
import com.pramurta.movie.repositories.AvailableShowRepository;
import com.pramurta.movie.repositories.MovieRepository;
import com.pramurta.movie.repositories.MovieTheatreRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AvailableShowService {
    private final AvailableShowRepository availableShowRepository;
    private final MovieRepository movieRepository;
    private final MovieTheatreRepository movieTheatreRepository;

    public AvailableShowService(AvailableShowRepository availableShowRepository, MovieRepository movieRepository,
                                MovieTheatreRepository movieTheatreRepository) {
        this.availableShowRepository = availableShowRepository;
        this.movieRepository = movieRepository;
        this.movieTheatreRepository = movieTheatreRepository;
    }

    public List<AvailableShow> getShowsByMovieName(String movieName) throws Exception {
        if(!movieRepository.existsById(movieName)) {
            throw new Exception(String.format("%s movie doesn't exist.",movieName));
        }
        return availableShowRepository.findShowsByMovieName(movieName);
    }

    public List<AvailableShow> getShowsByTheatreName(String theatreName) throws Exception {
        if(!movieTheatreRepository.existsById(theatreName)) {
            throw new Exception(String.format("%s theatre doesn't exist.",theatreName));
        }
        return availableShowRepository.findShowsByTheatreName(theatreName);
    }

    public List<AvailableShow> getShowsByMovieNameAndDateTimeRange(String movieName, String fromTime,
                                                                   String toTime) throws Exception {
        if(!movieRepository.existsById(movieName)) {
            throw new Exception(String.format("%s movie doesn't exist.",movieName));
        }
        LocalDateTime startTime = LocalDateTime.parse(fromTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime endTime = LocalDateTime.parse(toTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        return availableShowRepository.findShowsByMovieNameAndDateTimeRange(movieName,startTime,endTime);
    }

    public List<AvailableShow> getShowsByTheatreNameAndDateTimeRange(String theatreName, String fromTime,
                                                                   String toTime) throws Exception {
        if(!movieTheatreRepository.existsById(theatreName)) {
            throw new Exception(String.format("%s theatre doesn't exist.",theatreName));
        }
        LocalDateTime startTime = LocalDateTime.parse(fromTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime endTime = LocalDateTime.parse(toTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        return availableShowRepository.findShowsByTheatreNameAndDateTimeRange(theatreName,startTime,endTime);
    }

    public List<AvailableShow> getShowsWithinDateTimeRange(String fromTime, String toTime) {
        LocalDateTime startTime = LocalDateTime.parse(fromTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime endTime = LocalDateTime.parse(toTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        return availableShowRepository.findShowsInTimeRange(startTime,endTime);
    }


}
