package com.pramurta.movie.controllers;

import com.pramurta.movie.domain.dtos.PersonDto;
import com.pramurta.movie.domain.dtos.UpdatePersonDto;
import com.pramurta.movie.domain.entities.Person;
import com.pramurta.movie.helpers.ResponseHelper;
import com.pramurta.movie.mappers.PersonMapper;
import com.pramurta.movie.services.PersonService;
import com.pramurta.movie.services.validators.PersonValidationResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Period;
import java.util.Optional;

@RestController
public class PersonController {
    private final PersonService personService;
    private final PersonMapper personMapper;

    public PersonController(PersonService personService, PersonMapper personMapper) {
        this.personService = personService;
        this.personMapper = personMapper;
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

    @PostMapping(path = "/persons/signup")
    public ResponseEntity<?> createPerson(@RequestBody PersonDto personPayload) {
        try {
            Person person = personMapper.mapToEntity(personPayload);
            PersonValidationResult validationResult = personService.createPerson(person);
            if(validationResult.getIsValid()) {
                PersonDto personDto = personMapper.mapToDto(validationResult.getPerson());
                return new ResponseEntity<>(ResponseHelper.constructSuccessfulAPIResponse(personDto)
                        , HttpStatus.CREATED);
            }
            else {
                return new ResponseEntity<>(
                        ResponseHelper.constructFailedAPIResponse(validationResult.getValidationMessage())
                        ,HttpStatus.BAD_REQUEST);
            }
        } catch(Exception e) {
            return new ResponseEntity<>(ResponseHelper.constructFailedAPIResponse(e.getMessage())
                    ,HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/persons/{passportNumber}")
    public ResponseEntity<?> removePerson(@PathVariable("passportNumber") String passportNumber) {
        try {
            personService.removePerson(passportNumber);
            String responseMessage = String.format("Person with passport number %s has been removed from our database.",passportNumber);
            return new ResponseEntity<>(ResponseHelper.constructSuccessfulAPIResponse(responseMessage),HttpStatus.NO_CONTENT);
        } catch(Exception e) {
            return new ResponseEntity<>(ResponseHelper.constructFailedAPIResponse(e.getMessage())
                    ,HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(path = "/persons")
    public ResponseEntity<?> updatePersonDetails(@RequestBody UpdatePersonDto updatePersonPayload) {
        try {
            if(!personService.personExists(updatePersonPayload.getPassportNumber())) {
                String validationMessage = String.format("Person with passport number %s doesn't exist",updatePersonPayload.getPassportNumber());
                return new ResponseEntity<>(ResponseHelper.constructFailedAPIResponse(validationMessage),HttpStatus.NOT_FOUND);
            }
            Person updatedPerson = personService.updatePersonDetails(updatePersonPayload);
            return new ResponseEntity<>(ResponseHelper.constructSuccessfulAPIResponse(updatedPerson),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    ResponseHelper.constructFailedAPIResponse(e.getMessage()),HttpStatus.BAD_REQUEST);
        }

    }
}
