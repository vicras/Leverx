package com.vicras.service.impl;

import com.vicras.dto.PersonDto;
import com.vicras.exception.EntityNotFoundException;
import com.vicras.mapper.PersonMapper;
import com.vicras.model.Person;
import com.vicras.repository.PersonRepository;
import com.vicras.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author viktar hraskou
 */
@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    @Override
    public void addNewPerson(PersonDto personDto) {
        Person person = personMapper.toPerson(personDto);
        personRepository.save(person);
    }

    @Override
    public void updatePerson(PersonDto personDto) {
        Person person = personRepository.findById(personDto.getId())
                .map(oldPerson -> updateExistingPerson(oldPerson, personDto))
                .orElseThrow(() -> new EntityNotFoundException(Person.class, personDto.getId()));
        personRepository.save(person);
    }

    @Override
    public void removePersonById(Long id) {
        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException(Person.class, id);
        }
    }

    @Override
    public List<PersonDto> getAllPersons() {
        return personRepository.findAll().stream()
                .map(personMapper::fromPerson)
                .collect(toList());
    }

    @Override
    public Person getPersonById(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Person.class, id));
    }

    Person updateExistingPerson(Person oldPerson, PersonDto newPerson) {
        oldPerson.setName(newPerson.getName());
        oldPerson.setBirthday(newPerson.getBirthday());
        return oldPerson;
    }

}
