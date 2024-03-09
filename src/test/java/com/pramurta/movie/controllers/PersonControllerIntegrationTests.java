package com.pramurta.movie.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pramurta.movie.domain.Person;
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
    @Autowired
    public PersonControllerIntegrationTests(PersonService personService, MockMvc mockMvc, ObjectMapper objectMapper) {
        this.personService = personService;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void testThatPersonCanBeCreated() throws Exception {
        personService.removeAllPersons();
        Person person = TestDataUtil.createTestPerson();
        String personStr = objectMapper.writeValueAsString(person);
        mockMvc.perform(MockMvcRequestBuilders.post("/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(personStr))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Person1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cardNumber").value("Card1"));
    }

    @Test
    public void testThatPersonCanBeRemoved() throws Exception {
        personService.removeAllPersons();
        Person person = TestDataUtil.createTestPerson();
        personService.createPerson(person);
        personService.removePerson(person.getPassportNumber());
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
        person.setCardNumber("Different Card");
        String personStr = objectMapper.writeValueAsString(person);
        mockMvc.perform(MockMvcRequestBuilders.patch("/persons/"+person.getPassportNumber())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(personStr))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(person.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cardNumber").value(person.getCardNumber()));
    }
}
