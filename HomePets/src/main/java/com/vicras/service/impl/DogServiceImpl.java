package com.vicras.service.impl;

import com.vicras.dto.DogDto;
import com.vicras.exception.EntityNotFoundException;
import com.vicras.mapper.DogMapper;
import com.vicras.model.Dog;
import com.vicras.repository.DogRepository;
import com.vicras.service.DogService;
import com.vicras.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

/**
 * @author viktar hraskou
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DogServiceImpl implements DogService {

    private final PersonService personService;
    private final DogRepository dogRepository;
    private final DogMapper dogMapper;

    @Override
    public List<DogDto> getAll() {
        return dogRepository.findAll().stream()
                .map(dogMapper::fromDog)
                .collect(toList());
    }

    @Override
    public DogDto getById(Long id) {
        return dogRepository.findById(id)
                .map(dogMapper::fromDog)
                .orElseThrow(() -> new EntityNotFoundException(Dog.class, id));
    }

    @Override
    public void updateDog(DogDto dogDto) {
        Dog dog = dogRepository.findById(dogDto.getId())
                .map(oldDog -> updateExistingDog(oldDog, dogDto))
                .orElseThrow(() -> new EntityNotFoundException(Dog.class, dogDto.getId()));
        dogRepository.save(dog);
        log.info(format("dog with id=%d updated %s", dogDto.getId(), dog));
    }

    @Override
    public void addDog(DogDto dogDto) {
        Dog dog = dogMapper.toDog(dogDto);
        dogRepository.save(dog);
        log.info(format("dog with id=%d added %s", dogDto.getId(), dog));
    }

    @Override
    public void removeDogById(Long id) {
        if (dogRepository.existsById(id)) {
            dogRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException(Dog.class, id);
        }
        log.info(format("dog with id=%d removed", id));
    }

    Dog updateExistingDog(Dog oldDog, DogDto newDog) {
        oldDog.setName(newDog.getName());
        oldDog.setBreed(newDog.getBreed());
        oldDog.setOwner(personService.getPersonById(newDog.getOwner()));
        return oldDog;
    }

}
