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
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class AnimalServiceImpl implements AnimalService {

    private final PersonService personService;
    private final AnimalRepository animalRepository;

    @Override
    public void exchangeAnimals(ExchangeDto exchangeDto) {

        Person ownerFrom = personService.getPersonById(exchangeDto.getOwnerFrom());
        Person ownerTo = personService.getPersonById(exchangeDto.getOwnerTo());

        List<Animal> animalsTo = getAnimalsByIds(exchangeDto.getAnimalsTo());
        List<Animal> animalsFrom = getAnimalsByIds(exchangeDto.getAnimalsFrom());

        checkAnimalExisting(animalsFrom, exchangeDto.getAnimalsFrom());
        checkAnimalExisting(animalsTo, exchangeDto.getAnimalsTo());

        checkAnimalAffiliation(ownerFrom.getId(), animalsFrom);
        checkAnimalAffiliation(ownerTo.getId(), animalsTo);

        setOwnerForAnimals(animalsFrom, ownerTo);
        setOwnerForAnimals(animalsTo, ownerFrom);
    }

    private List<Animal> getAnimalsByIds(List<Long> animalsTo) {
        return animalRepository.findAllById(animalsTo);
    }

    private void checkAnimalExisting(List<Animal> animals, List<Long> animalsIds) {
        var findAnimalIds = animals.stream().map(Animal::getId).collect(toList());
        animalsIds.stream()
                .filter(id -> !findAnimalIds.contains(id))
                .findFirst()
                .ifPresent((id) -> {
                    throw new EntityNotFoundException(Animal.class, id);
                });
    }

    private void checkAnimalAffiliation(Long ownerId, List<Animal> animalsFrom) {
        animalsFrom.stream()
                .filter(animal -> !isAnimalBelongPersonWithId(ownerId, animal))
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
