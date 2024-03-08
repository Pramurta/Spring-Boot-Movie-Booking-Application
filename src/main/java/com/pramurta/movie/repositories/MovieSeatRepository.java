package com.pramurta.movie.repositories;

import com.pramurta.movie.domain.MovieSeat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieSeatRepository extends MongoRepository<MovieSeat, String> {}
