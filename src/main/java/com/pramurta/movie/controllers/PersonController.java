package com.pramurta.movie.controllers;

import com.pramurta.movie.controllers.helpers.ControllerHelper;
import com.pramurta.movie.domain.dtos.PersonDto;
import com.pramurta.movie.domain.dtos.UpdatePersonDto;
import com.pramurta.movie.domain.dtos.UserLoginDto;
import com.pramurta.movie.domain.entities.Person;
import com.pramurta.movie.helpers.ResponseHelper;
import com.pramurta.movie.mappers.PersonMapper;
import com.pramurta.movie.services.PersonService;
import com.pramurta.movie.services.validators.PersonValidationResult;
import com.pramurta.movie.services.validators.PersonValidator;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class PersonController {
    private final PersonService personService;
    private final PersonMapper personMapper;
    private PersonValidator personValidator;


    public PersonController(PersonService personService, PersonMapper personMapper, PersonValidator personValidator) {
        this.personService = personService;
        this.personMapper = personMapper;
        this.personValidator = personValidator;
    }

    @GetMapping(path = "/persons/{passportNumber}")
    public ResponseEntity<?> getPerson(@PathVariable("passportNumber") String passportNumber, HttpSession httpSession) {
        Optional<Person> person = personService.findPersonByPassportNumber(passportNumber);
        if(person.isPresent()) {
            if(!ControllerHelper.isUserLoggedIn(httpSession)) {
                return new ResponseEntity<>(ResponseHelper.constructFailureResponse("Please login first to view your details"),HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(person.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(ResponseHelper.constructFailureResponse(String.format("User with passport number: %s not found in the database",passportNumber)),HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/persons/signup")
    public ResponseEntity<?> signup(@RequestBody PersonDto personPayload) {
        try {
            Person person = personMapper.mapToEntity(personPayload);
            PersonValidationResult validationResult = personService.createPerson(person);
            if(validationResult.getIsValid()) {
                PersonDto personDto = personMapper.mapToDto(validationResult.getPerson());
                return new ResponseEntity<>(ResponseHelper.constructSuccessResponse(personDto)
                        , HttpStatus.CREATED);
            }
            else {
                return new ResponseEntity<>(
                        ResponseHelper.constructFailureResponse(validationResult.getValidationMessage())
                        ,HttpStatus.BAD_REQUEST);
            }
        } catch(Exception e) {
            return new ResponseEntity<>(ResponseHelper.constructFailureResponse(e.getMessage())
                    ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/persons/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto userLoginDto, HttpSession httpSession) throws Exception {
        try {
            UserLoginDto userLoginDtoValidated = personService.login(userLoginDto);
            Person person = personService.findPersonByPassportNumber(userLoginDtoValidated.getPassportNumber()).get();
            httpSession.setAttribute("username", person.getPassportNumber());
            httpSession.setAttribute("userRoles",person.getUserRoles());
            return new ResponseEntity<>(ResponseHelper.constructSuccessResponse(
                    String.format("User: %s logged in successfully!",userLoginDtoValidated.getPassportNumber()))
                    ,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/persons/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return new ResponseEntity<>(ResponseHelper.constructSuccessResponse("Logged out successfully!"), HttpStatus.OK);
    }
    @DeleteMapping(path = "/persons/{passportNumber}")
    public ResponseEntity<?> removePerson(@PathVariable("passportNumber") String passportNumber, HttpSession httpSession) {
        try {
            if(!ControllerHelper.isUserLoggedIn(httpSession)) {
                return new ResponseEntity<>(ResponseHelper.constructFailureResponse("Please login first to remove a person"),HttpStatus.UNAUTHORIZED);
            }
            if(!ControllerHelper.isUserAppAdmin(httpSession)) {
                return new ResponseEntity<>(ResponseHelper.constructFailureResponse("Only app admins are allowed to remove a person"),HttpStatus.UNAUTHORIZED);
            }
            personService.removePerson(passportNumber);
            String responseMessage = String.format("Person with passport number %s has been removed from our database.",passportNumber);
            return new ResponseEntity<>(ResponseHelper.constructSuccessResponse(responseMessage),HttpStatus.NO_CONTENT);
        } catch(Exception e) {
            return new ResponseEntity<>(ResponseHelper.constructFailureResponse(e.getMessage())
                    ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(path = "/persons")
    public ResponseEntity<?> updatePersonDetails(@RequestBody UpdatePersonDto updatePersonPayload, HttpSession httpSession) {
        try {
            if(!personService.personExists(updatePersonPayload.getPassportNumber())) {
                String validationMessage = String.format("Person with passport number %s doesn't exist",updatePersonPayload.getPassportNumber());
                return new ResponseEntity<>(ResponseHelper.constructFailureResponse(validationMessage),HttpStatus.NOT_FOUND);
            }
            if(!ControllerHelper.isUserLoggedIn(httpSession)) {
                return new ResponseEntity<>(ResponseHelper.constructFailureResponse(ControllerHelper.USER_NOT_LOGGED_IN_MESSAGE),HttpStatus.UNAUTHORIZED);
            }
            Person updatedPerson = personService.updatePersonDetails(updatePersonPayload);
            return new ResponseEntity<>(ResponseHelper.constructSuccessResponse(updatedPerson),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    ResponseHelper.constructFailureResponse(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
