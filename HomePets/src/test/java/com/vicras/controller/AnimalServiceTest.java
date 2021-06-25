package com.vicras.controller;

import com.vicras.dto.ExchangeDto;
import com.vicras.exception.AnimalsNotBelongException;
import com.vicras.exception.EntityNotFoundException;
import com.vicras.model.Animal;
import com.vicras.model.Person;
import com.vicras.repository.AnimalRepository;
import com.vicras.service.AnimalService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import javax.validation.constraints.NotNull;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;

@Sql(scripts = {"person-db-init.sql", "dog-db-init.sql", "cat-db-init.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "person-db-remove.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest
class AnimalServiceTest {


    @Autowired
    private AnimalService animalService;

    @Autowired
    private AnimalRepository animalRepository;

    private final List<@NotNull Long> animalsFromIds = List.of(3L, 7L);
    private final List<@NotNull Long> animalsToIds = List.of(6L);
    private final long ownerFromId = 1L;
    private final long ownerToId = 2L;

    @Test
    void exchangeAnimals() {

        //given
        var exchangeDto = ExchangeDto.builder()
                .ownerFrom(ownerFromId)
                .animalsFrom(animalsFromIds)
                .ownerTo(ownerToId)
                .animalsTo(animalsToIds)
                .build();

        //when
        animalService.exchangeAnimals(exchangeDto);

        //then
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
    void exchangeNonExistingAnimal() {
        //given
        var exchangeDto = ExchangeDto.builder()
                .ownerFrom(ownerFromId)
                .animalsFrom(List.of(23L))
                .ownerTo(ownerToId)
                .animalsTo(animalsToIds)
                .build();

        //when
        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> animalService.exchangeAnimals(exchangeDto)
        );
    }

    @Test
    void exchangeNonBelongAnimal() {
        //given
        var exchangeDto = ExchangeDto.builder()
                .ownerFrom(ownerFromId)
                .animalsFrom(List.of(6L))
                .ownerTo(ownerToId)
                .animalsTo(animalsToIds)
                .build();

        //when
        Assertions.assertThrows(
                AnimalsNotBelongException.class,
                () -> animalService.exchangeAnimals(exchangeDto)
        );
    }

}