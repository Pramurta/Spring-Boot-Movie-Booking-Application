package com.pramurta.movie.repositories;

import com.pramurta.movie.domain.entities.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends MongoRepository<Person,String> {}
