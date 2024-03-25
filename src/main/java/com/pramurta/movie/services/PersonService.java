package com.pramurta.movie.services;

import com.pramurta.movie.domain.dtos.UpdatePersonDto;
import com.pramurta.movie.domain.dtos.UserLoginDto;
import com.pramurta.movie.domain.entities.Person;
import com.pramurta.movie.repositories.MovieBookingRepository;
import com.pramurta.movie.repositories.PersonRepository;
import com.pramurta.movie.services.validators.PersonValidator;
import com.pramurta.movie.services.validators.PersonValidationResult;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final PersonValidator personValidator;

    private final BCryptPasswordEncoder passwordEncoder;

    private final MovieBookingRepository movieBookingRepository;

    public PersonService(PersonRepository personRepository, PersonValidator personValidator, BCryptPasswordEncoder passwordEncoder, MovieBookingRepository movieBookingRepository) {
        this.personRepository = personRepository;
        this.personValidator = personValidator;
        this.passwordEncoder = passwordEncoder;
        this.movieBookingRepository = movieBookingRepository;
    }
    public PersonValidationResult createPerson(Person personPayload) {
        PersonValidationResult personValidationResult = personValidator.validateCreatePerson(personPayload);
        if(personValidationResult.getIsValid()) {
            Person person = personValidationResult.getPerson();
            String encryptedPassword = passwordEncoder.encode(person.getPassword());
            person.setPassword(encryptedPassword);
            personValidationResult.setPerson(personRepository.save(person));
        }
        else {
            personValidationResult.setPerson(null);
        }
        return personValidationResult;
    }

    public Optional<Person> findPersonByPassportNumber(String passportNumber) {
        return personRepository.findById(passportNumber);
    }

    public boolean personExists(String passportNumber) {
        return personRepository.existsById(passportNumber);
    }

    public Person updatePersonDetails(UpdatePersonDto updatePersonPayload) throws Exception {
        Person existingPerson = personRepository.findById(updatePersonPayload.getPassportNumber()).get();
        PersonValidationResult personValidationResult = PersonValidationResult.builder()
                .isValid(true)
                .validationMessage("")
                .build();
        personValidator.validateUpdatePerson(updatePersonPayload, personValidationResult, existingPerson);
        if(!personValidationResult.getIsValid()) {
            throw new Exception(personValidationResult.getValidationMessage());
        }
        if(updatePersonPayload.getName()!=null) {
            existingPerson.setName(updatePersonPayload.getName());
        }
        if(updatePersonPayload.getOldPassword()!=null) {
            existingPerson.setPassword(passwordEncoder.encode(updatePersonPayload.getNewPassword()));
        }
        return personRepository.save(existingPerson);
    }
    public void removePerson(String passportNumber) throws Exception {
        if(!personExists(passportNumber)) {
            throw new Exception(String.format("Person with passport number: %s, doesn't exist.",passportNumber));
        }
        if(!movieBookingRepository.findByPassportNumber(passportNumber).isEmpty()) {
            throw new Exception(String.format("The person with passport number: %s, has movie bookings. Delete the movie bookings first.",
                    passportNumber));
        }
        personRepository.deleteById(passportNumber);
    }

    public void removeAllPersons() {
        personRepository.deleteAll();
    }

    public UserLoginDto login(UserLoginDto userLoginDto) throws Exception {
        return personValidator.validateLogin(userLoginDto);
    }
}
