package com.pramurta.movie.services.validators;

import com.pramurta.movie.domain.dtos.UpdatePersonDto;
import com.pramurta.movie.domain.dtos.UserLoginDto;
import com.pramurta.movie.domain.entities.Person;
import com.pramurta.movie.repositories.PersonRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PersonValidator {
    private final PersonRepository personRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public PersonValidator(PersonRepository personRepository, BCryptPasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public PersonValidationResult validateCreatePerson(Person personPayload) {
        PersonValidationResult personValidationResult = PersonValidationResult.builder()
                .isValid(true)
                .validationMessage("")
                .build();
        if(personPayload==null) {
            personValidationResult.setIsValid(false);
            personValidationResult.setValidationMessage("The entire payload is null");
            return personValidationResult;
        }
        if(personPayload.getPassportNumber()==null) {
            personValidationResult.setIsValid(false);
            personValidationResult.setValidationMessage("Passport Number missing in payload");
            return personValidationResult;
        }
        if(personPayload.getUserRoles()==null) {
            personValidationResult.setIsValid(false);
            personValidationResult.setValidationMessage("User role missing in payload");
            return personValidationResult;
        }
        if(personPayload.getName()==null) {
            personValidationResult.setIsValid(false);
            personValidationResult.setValidationMessage("User name is missing in payload");
            return personValidationResult;
        }
        if(personPayload.getPassword()==null) {
            personValidationResult.setIsValid(false);
            personValidationResult.setValidationMessage("Password is missing in payload");
            return personValidationResult;
        }

        //Passport number checks
        if(personRepository.existsById(personPayload.getPassportNumber())) {
            personValidationResult.setIsValid(false);
            personValidationResult.setValidationMessage(String.format("The person with the passport number %s already exists!"
                    ,personPayload.getPassportNumber()));
            return personValidationResult;
        }

        //Password checks
        String password = personPayload.getPassword();
        validatePassword(password,personValidationResult);

        personValidationResult.setPerson(personPayload);
        personValidationResult.setValidationMessage("Signup successful!");
        return personValidationResult;
    }

    public void validateUpdatePerson(UpdatePersonDto updatePersonPayload,
                                                       PersonValidationResult personValidationResult,
                                     Person existingPerson) {
        if(updatePersonPayload.getOldPassword()!=null) {
            if(updatePersonPayload.getNewPassword()==null) {
                personValidationResult.setIsValid(false);
                personValidationResult.setValidationMessage("Both old and new passwords must be present");
            }
            if(!passwordEncoder.matches(updatePersonPayload.getOldPassword(),existingPerson.getPassword())) {
                personValidationResult.setIsValid(false);
                personValidationResult.setValidationMessage("Old password doesn't match with the one in the database");
            }
            validatePassword(updatePersonPayload.getNewPassword(), personValidationResult);
        }
    }

    public void validatePassword(String password, PersonValidationResult personValidationResult) {
        if(password.isEmpty()) {
            personValidationResult.setIsValid(false);
            personValidationResult.setValidationMessage("Password can't be empty");
        }
        if(password.length()<6) {
            personValidationResult.setIsValid(false);
            personValidationResult.setValidationMessage("Password length must be at least 6 characters");
        }
    }

    public UserLoginDto validateLogin(UserLoginDto userLoginDto) throws Exception{
        String passportNumber = userLoginDto.getPassportNumber();
        String password = userLoginDto.getPassword();
        if(!personRepository.existsById(passportNumber)) {
            throw new Exception(String.format("Person with passport number: %s doesn't exist.",passportNumber));
        }
        Person person = personRepository.findById(passportNumber).get();
        if(!passwordEncoder.matches(password,person.getPassword())) {
            throw new Exception("Password provided is incorrect");
        }
        return userLoginDto;
    }






}
