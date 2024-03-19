package com.pramurta.movie.repositories;

import com.pramurta.movie.domain.entities.MovieBooking;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MovieBookingRepository extends MongoRepository<MovieBooking, String> {
    List<MovieBooking> findByShowId(ObjectId showId);

    List<MovieBooking> findByPassportNumber(String passportNumber);
}
