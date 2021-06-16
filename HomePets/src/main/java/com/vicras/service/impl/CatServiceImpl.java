package com.vicras.service.impl;

import com.vicras.dto.CatDto;
import com.vicras.exception.EntityNotFoundException;
import com.vicras.mapper.CatMapper;
import com.vicras.model.Cat;
import com.vicras.model.Person;
import com.vicras.repository.CatRepository;
import com.vicras.repository.PersonRepository;
import com.vicras.service.CatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

/**
 * @author viktar hraskou
 */
@Service
@RequiredArgsConstructor
public class CatServiceImpl implements CatService {

    private final PersonRepository personRepository;
    private final CatRepository catRepository;
    private final CatMapper catMapper;

    @Override
    public List<CatDto> getAll() {
        return catRepository.findAll().stream()
                .map(catMapper::fromCat)
                .collect(toList());
    }

    @Override
    public List<CatDto> getById(Long id) {
        return catRepository.findById(id)
                .map(catMapper::fromCat)
                .map(List::of)
                .orElse(emptyList());
    }

    @Override
    public void updateCat(CatDto catDto) {
        Cat cat = catRepository.findById(catDto.getId())
                .map(oldDog -> updateExistingCat(oldDog, catDto))
                .orElseThrow(() -> new EntityNotFoundException(Cat.class, catDto.getId()));
        catRepository.save(cat);
    }

    @Override
    public void addCat(CatDto catDto) {
        Cat cat = catMapper.toCat(catDto);
        catRepository.save(cat);
    }

    @Override
    public void removeCatById(Long id) {
        if (catRepository.existsById(id)) {
            catRepository.deleteById(id);
        }
    }

    Cat updateExistingCat(Cat oldCat, CatDto newCat) {
        oldCat.setName(newCat.getName());
        oldCat.setBreed(newCat.getBreed());
        oldCat.setOwner(getPersonById(newCat.getOwner()));
        return oldCat;
    }

    Person getPersonById(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Person.class, id));
    }
}
