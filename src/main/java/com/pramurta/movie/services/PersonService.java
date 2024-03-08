package com.pramurta.movie.services;

import com.pramurta.movie.domain.Person;
import com.pramurta.movie.repositories.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    public Person createPerson(Person person) {
        if(personExists(person.getPassportNumber())) {
            throw new RuntimeException("The person with the passport number already exists!");
        }
        return personRepository.save(person);
    }

    public Optional<Person> findPersonByPassportNumber(String passportNumber) {
        return personRepository.findById(passportNumber);
    }

    public boolean personExists(String passportNumber) {
        return personRepository.existsById(passportNumber);
    }

    public Person updatePersonDetails(String passportNumber, Person updatePersonPayload) {
        if(!personExists(passportNumber)) {
            throw new RuntimeException("Person with the passport number: "+passportNumber
                    +" doesn't exist");
        }
        else {
            Person existingPerson = personRepository.findById(passportNumber).get();
            if(updatePersonPayload.getName()!=null) {
                existingPerson.setName(updatePersonPayload.getName());
            }
            if(updatePersonPayload.getCardNumber()!=null) {
                existingPerson.setCardNumber(updatePersonPayload.getCardNumber());
            }
            return personRepository.save(existingPerson);
        }
    }

    public void removePerson(String passportNumber) {
        if(!personExists(passportNumber)) {
            throw new RuntimeException("Person with the passport number: "+passportNumber
            +" doesn't exist");
        }
        else {
            personRepository.deleteById(passportNumber);
        }
    }
}
