package com.pramurta.movie.repositories;

import com.pramurta.movie.domain.MovieTheatre;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieTheatreRepository extends MongoRepository<MovieTheatre, String> {}
