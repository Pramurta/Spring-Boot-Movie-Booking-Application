package com.pramurta.movie.repositories;

import com.pramurta.movie.domain.AvailableShow;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AvailableShowRepository extends MongoRepository<AvailableShow,String> {
}
