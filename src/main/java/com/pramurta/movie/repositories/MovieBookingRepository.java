package com.pramurta.movie.repositories;

import com.pramurta.movie.domain.MovieBooking;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieBookingRepository extends MongoRepository<MovieBooking, String> {
}
