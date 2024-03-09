package com.pramurta.movie.services;

import com.pramurta.movie.domain.Person;
import com.pramurta.movie.repositories.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    private static final String PERSON_NOT_FOUND_TEMPLATE = "Person with the passport number %s doesn't exist";

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    public Person createPerson(Person person) {
        if(personExists(person.getPassportNumber())) {
            throw new RuntimeException(String.format("The person with the passport number %s already exists!"
                    ,person.getPassportNumber()));
        }
        return personRepository.save(person);
    }

    public Optional<Person> findPersonByPassportNumber(String passportNumber) {
        return personRepository.findById(passportNumber);
    }

    public boolean personExists(String passportNumber) {
        return personRepository.existsById(passportNumber);
    }

    public Person updatePersonDetails(Person updatePersonPayload) {
        if(!personExists(updatePersonPayload.getPassportNumber())) {
            throw new RuntimeException(String.format(PERSON_NOT_FOUND_TEMPLATE,updatePersonPayload.getPassportNumber()));
        }
            Person existingPerson = personRepository.findById(updatePersonPayload.getPassportNumber()).get();
            if(updatePersonPayload.getName()!=null) {
                existingPerson.setName(updatePersonPayload.getName());
            }
            if(updatePersonPayload.getCardNumber()!=null) {
                existingPerson.setCardNumber(updatePersonPayload.getCardNumber());
            }
            return personRepository.save(existingPerson);

    }
    public void removePerson(String passportNumber) {
        personRepository.deleteById(passportNumber);
    }

    public void removeAllPersons() {
        personRepository.deleteAll();
    }
}
