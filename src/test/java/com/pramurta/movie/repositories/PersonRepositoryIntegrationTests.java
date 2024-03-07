package com.pramurta.movie.repositories;

import com.pramurta.movie.domain.Person;
import com.pramurta.movie.utils.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PersonRepositoryIntegrationTests {

    private final PersonRepository personRepository;

    @Autowired
    public PersonRepositoryIntegrationTests(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Test
    public void testThatPersonCanBeCreatedAndRecalled() {
        Person person = TestDataUtil.createTestPerson();
        personRepository.save(person);
        Optional<Person> result = personRepository.findById("PN1");
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(person);
    }

    @Test
    public void testThatPersonDetailsAreUpdated() {
        Person person = TestDataUtil.createTestPerson();
        personRepository.save(person);
        person.setName("updated person");
        personRepository.save(person);
        Optional<Person> result = personRepository.findById("PN1");
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(person);
    }

    @Test
    public void testThatPersonCanBeDeleted() {
        Person person = TestDataUtil.createTestPerson();
        personRepository.save(person);
        personRepository.deleteById(person.getPassportNumber());
        Optional<Person> result = personRepository.findById("PN1");
        assertThat(result).isEmpty();
    }
}
