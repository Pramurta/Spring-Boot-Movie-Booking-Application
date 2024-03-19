package com.pramurta.movie.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pramurta.movie.domain.dtos.PersonDto;
import com.pramurta.movie.domain.dtos.UpdatePersonDto;
import com.pramurta.movie.domain.entities.Person;
import com.pramurta.movie.mappers.PersonMapper;
import com.pramurta.movie.services.PersonService;
import com.pramurta.movie.utils.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PersonControllerIntegrationTests {
    private final PersonService personService;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final PersonMapper personMapper;
    @Autowired
    public PersonControllerIntegrationTests(PersonService personService, MockMvc mockMvc, ObjectMapper objectMapper, PersonMapper personMapper) {
        this.personService = personService;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.personMapper = personMapper;
    }

    @Test
    public void testThatPersonCanBeSignedUp() throws Exception {
        personService.removeAllPersons();
        Person person = TestDataUtil.createTestPerson();
        PersonDto personDto = personMapper.mapToDto(person);
        String personStr = objectMapper.writeValueAsString(personDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/persons/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(personStr))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.name").value("Person1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.passportNumber").value("PN1"));
        personService.removeAllPersons();
    }

    @Test
    public void testThatPersonCanBeRemoved() throws Exception {
        personService.removeAllPersons();
        Person person = TestDataUtil.createTestPerson();
        personService.createPerson(person);
        mockMvc.perform(MockMvcRequestBuilders.delete("/persons/"+person.getPassportNumber())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
    @Test
    public void testThatPersonDetailsCanBeUpdated() throws Exception {
        personService.removeAllPersons();
        Person person = TestDataUtil.createTestPerson();
        personService.createPerson(person);
        person.setName("Different name");
        UpdatePersonDto updatePersonPayload = UpdatePersonDto.builder()
                .name(person.getName())
                .passportNumber(person.getPassportNumber())
                .build();
        String updatePersonPayloadStr = objectMapper.writeValueAsString(updatePersonPayload);
        mockMvc.perform(MockMvcRequestBuilders.patch("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatePersonPayloadStr))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.name").value(person.getName()));
        personService.removeAllPersons();
    }
}
