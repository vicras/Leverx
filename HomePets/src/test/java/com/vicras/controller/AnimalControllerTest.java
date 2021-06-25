package com.vicras.controller;

import com.vicras.model.Animal;
import com.vicras.model.Person;
import com.vicras.repository.AnimalRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.constraints.NotNull;
import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(scripts = {"person-db-init.sql", "dog-db-init.sql", "cat-db-init.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "person-db-remove.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
public class AnimalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AnimalRepository animalRepository;

    private final List<@NotNull Long> animalsFromIds = List.of(3L, 7L);
    private final List<@NotNull Long> animalsToIds = List.of(6L);
    private final long ownerFromId = 1L;
    private final long ownerToId = 2L;

    private final String jsonExchangeTemplate = "{\n" +
            "    \"owner_from\": \"%d\",\n" +
            "    \"animals_from\": [%s],\n" +
            "    \"owner_to\": \"%d\",\n" +
            "    \"animals_to\":[%s]\n" +
            "}\n";

    @Test
    public void ExchangeAnimals() throws Exception {

        //given
        String json = format(jsonExchangeTemplate,
                ownerFromId, listToString(animalsFromIds), ownerToId, listToString(animalsToIds));

        //when
        mockMvc.perform(post("/animals/exchange")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))

                //then

                .andDo(print())
                .andExpect(status().isOk());

        assertThat(animalOwnerIds(animalsFromIds), everyItem(is(ownerToId)));
        assertThat(animalOwnerIds(animalsToIds), everyItem(is(ownerFromId)));
    }

    private List<Long> animalOwnerIds(List<Long> animalsIds) {
        return animalRepository.findAllById(animalsIds).stream()
                .map(Animal::getOwner)
                .map(Person::getId)
                .collect(toList());
    }

    @Test
    public void animalsShouldNotRepeat() throws Exception {

        //given
        String json = format(jsonExchangeTemplate,
                ownerFromId, "2, 3", ownerToId, "1, 1");

        //when
        mockMvc.perform(post("/animals/exchange")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))

                //then

                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    public void userDontExist() throws Exception {

        //given
        String json = format(jsonExchangeTemplate,
                5, "", ownerToId, "");

        //when
        mockMvc.perform(post("/animals/exchange")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))

                //then

                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    public void animalNotBelong() throws Exception {

        //given
        String json = format(jsonExchangeTemplate,
                ownerFromId, listToString(animalsToIds), ownerToId, listToString(animalsFromIds));

        //when
        mockMvc.perform(post("/animals/exchange")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))

                //then

                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    private String listToString(List<Long> animalsIds) {
        return animalsIds.stream()
                .map(Object::toString)
                .collect(joining(", "));
    }

}