package com.pramurta.movie.mappers;

import com.pramurta.movie.domain.dtos.PersonDto;
import com.pramurta.movie.domain.entities.Person;
import com.pramurta.movie.domain.enums.UserRole;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper implements Mapper<Person, PersonDto>{

    @Override
    public PersonDto mapToDto(Person person) {
        return PersonDto.builder()
                .passportNumber(person.getPassportNumber())
                .userRoles(person.getUserRoles().stream().map(UserRole::toString).toList())
                .name(person.getName())
                .password(person.getPassword())
                .build();
    }

    @Override
    public Person mapToEntity(PersonDto personDto) {
        return Person.builder()
                .password(personDto.getPassword())
                .name(personDto.getName())
                .passportNumber(personDto.getPassportNumber())
                .userRoles(personDto.getUserRoles().stream().map(UserRole::valueOf).toList())
                .build();
    }
}
