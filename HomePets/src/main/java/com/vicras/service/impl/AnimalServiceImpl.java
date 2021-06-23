package com.vicras.service.impl;

import com.vicras.dto.ExchangeDto;
import com.vicras.exception.AnimalsNotBelongException;
import com.vicras.exception.EntityNotFoundException;
import com.vicras.model.Animal;
import com.vicras.model.Person;
import com.vicras.repository.AnimalRepository;
import com.vicras.service.AnimalService;
import com.vicras.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.lang.String.format;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AnimalServiceImpl implements AnimalService {

    private final PersonService personService;
    private final AnimalRepository animalRepository;

    @Override
    public void exchangeAnimals(ExchangeDto exchangeDto) throws EntityNotFoundException, AnimalsNotBelongException {

        Person ownerFrom = getPersonById(exchangeDto.getOwnerFrom());
        Person ownerTo = getPersonById(exchangeDto.getOwnerTo());

        List<Animal> animalsTo = getAnimalsByIds(exchangeDto.getAnimalsTo());
        List<Animal> animalsFrom = getAnimalsByIds(exchangeDto.getAnimalsFrom());

        checkAnimalExisting(animalsFrom, exchangeDto.getAnimalsFrom());
        checkAnimalExisting(animalsTo, exchangeDto.getAnimalsTo());

        checkAnimalAffiliation(ownerFrom.getId(), animalsFrom);
        checkAnimalAffiliation(ownerTo.getId(), animalsTo);

        setOwnerForAnimals(animalsFrom, ownerTo);
        setOwnerForAnimals(animalsTo, ownerFrom);

        logAnimals(animalsFrom, ownerTo);
        logAnimals(animalsTo, ownerFrom);
    }

    private void logAnimals(List<Animal> animalsFrom, Person ownerTo) {
        String animals = animalsFrom.stream()
                .map(animal -> animal.getName() + " with id=" + animal.getId())
                .collect(joining(", "));
        log.info(format("animals %s go over user with id=%d", animals, ownerTo.getId()));
    }

    private Person getPersonById(Long ownerFrom) {
        return personService.getPersonById(ownerFrom);
    }

    private List<Animal> getAnimalsByIds(List<Long> animalsTo) {
        return animalRepository.findAllById(animalsTo);
    }

    private void checkAnimalExisting(List<Animal> animals, List<Long> animalsIds) throws EntityNotFoundException {
        var findAnimalIds = animals.stream().map(Animal::getId).collect(toList());

        animalsIds.stream()
                .filter(not(findAnimalIds::contains))
                .findFirst()
                .ifPresent((id) -> {
                    throw new EntityNotFoundException(Animal.class, id);
                });
    }

    private void checkAnimalAffiliation(Long ownerId, List<Animal> animalsFrom) throws AnimalsNotBelongException {
        animalsFrom.stream()
                .filter(not(animal -> isAnimalBelongPersonWithId(ownerId, animal)))
                .findFirst()
                .ifPresent(animal -> {
                    throw new AnimalsNotBelongException(animal, ownerId);
                });
    }

    private boolean isAnimalBelongPersonWithId(Long ownerId, Animal animal) {
        return animal.getOwner().getId().equals(ownerId);
    }

    private void setOwnerForAnimals(List<Animal> animals, Person owner) {
        animals.stream()
                .peek(animal -> animal.setOwner(owner))
                .forEach(animalRepository::save);
    }

}
