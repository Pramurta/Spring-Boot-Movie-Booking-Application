package com.pramurta.movie.controllers;

import com.pramurta.movie.domain.Person;
import com.pramurta.movie.services.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping(path = "/persons/{passportNumber}")
    public ResponseEntity<Person> getPerson(@PathVariable("passportNumber") String passportNumber) {
        Optional<Person> person = personService.findPersonByPassportNumber(passportNumber);
        if(person.isPresent()) {
            return new ResponseEntity<>(person.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/persons")
    public ResponseEntity<?> createPerson(@RequestBody Person personPayload) {
        try {
            return new ResponseEntity<>(personService.createPerson(personPayload), HttpStatus.CREATED);
        } catch(RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/persons/{passportNumber}")
    public ResponseEntity<Person> removePerson(@PathVariable("passportNumber") String passportNumber) {
        personService.removePerson(passportNumber);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
