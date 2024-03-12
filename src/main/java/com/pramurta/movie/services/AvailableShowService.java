package com.pramurta.movie.services;

import com.pramurta.movie.domain.entities.AvailableShow;
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

    public AvailableShow createAShow(AvailableShow availableShowPayload) throws Exception {
        if(!movieRepository.existsById(availableShowPayload.getMovieName())) {
            throw new Exception(String.format("The movie: %s doesn't exist",availableShowPayload.getMovieName()));
        }
        if(!movieTheatreRepository.existsById(availableShowPayload.getTheatreName())) {
            throw new Exception(String.format("The theatre: %s doesn't exist",availableShowPayload.getTheatreName()));
        }
        if(!movieTheatreRepository.findById(availableShowPayload.getTheatreName()).get().getHallNumbers().contains(availableShowPayload.getHallNumber())) {
            throw new Exception(String.format("The hall number: %s doesn't exist in the theatre: %s",availableShowPayload.getHallNumber(),availableShowPayload.getTheatreName()));
        }
        if(availableShowPayload.getFromTime().isBefore(LocalDateTime.now()) ||
                availableShowPayload.getToTime().isBefore(LocalDateTime.now())) {
            throw new Exception("The start Time or the end Time is before the current date");
        }
        if(availableShowPayload.getFromTime().isAfter(availableShowPayload.getToTime())) {
            throw new Exception("The start Time of the movie has to be lesser than the end Time");
        }
        List<AvailableShow> showsPlaying = checkWhetherAnyShowIsPlayingInTheHallWithinTheGivenTimeRange(
          availableShowPayload.getFromTime(),
          availableShowPayload.getToTime(),
          availableShowPayload.getTheatreName(),
          availableShowPayload.getHallNumber()
        );
        if(!showsPlaying.isEmpty()) {
            throw new Exception(String.format("The movie: %s is already playing in theatre: %s, at hall: %s, from %s, to %s",
                    showsPlaying.getFirst().getMovieName(),
                    showsPlaying.getFirst().getTheatreName(),
                    showsPlaying.getFirst().getHallNumber(),
                    showsPlaying.getFirst().getFromTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                    showsPlaying.getFirst().getToTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                    ));
        }
        return availableShowRepository.save(availableShowPayload);
    }

    public List<AvailableShow> checkWhetherAnyShowIsPlayingInTheHallWithinTheGivenTimeRange(LocalDateTime fromTime,
                                                                                         LocalDateTime toTime,
                                                                                         String theatreName,
                                                                                         int hallNumber) {
        return availableShowRepository.findByFromTimeAndToTimeAndTheatreNameAndHallNumber(fromTime,
                toTime,
                theatreName,
                hallNumber);
    }


}
