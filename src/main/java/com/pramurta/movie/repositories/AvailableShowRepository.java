package com.pramurta.movie.repositories;

import com.pramurta.movie.domain.AvailableShow;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface AvailableShowRepository extends MongoRepository<AvailableShow,String> {
    List<AvailableShow> findByFromTimeAndToTimeAndMovieNameAndTheatreNameAndHallNumber(LocalDateTime fromTime,
                                                                                       LocalDateTime toTime,
                                                                                       String movieName,
                                                                                       String theatreName,
                                                                                       int hallNumber);
    List<AvailableShow> findShowsByMovieName(String movieName);

    List<AvailableShow> findShowsByTheatreName(String theatreName);
    @Query("{ 'fromTime': { $gte: ?0 }, 'toTime': { $lte: ?1 } }")
    List<AvailableShow> findShowsInTimeRange(LocalDateTime fromTime, LocalDateTime toTime);

    @Query("{ 'movieName': ?0, 'fromTime': { $gte: ?1 }, 'toTime': { $lte: ?2 } }")
    List<AvailableShow> findShowsByMovieNameAndDateTimeRange(String movieName, LocalDateTime startTime, LocalDateTime endTime);

    @Query("{ 'theatreName': ?0, 'fromTime': { $gte: ?1 }, 'toTime': { $lte: ?2 } }")
    List<AvailableShow> findShowsByTheatreNameAndDateTimeRange(String theatreName, LocalDateTime startTime, LocalDateTime endTime);
}
