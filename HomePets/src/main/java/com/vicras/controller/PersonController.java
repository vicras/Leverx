package com.vicras.controller;

import com.vicras.dto.PersonDto;
import com.vicras.mapper.PersonMapper;
import com.vicras.model.Person;
import com.vicras.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author viktar hraskou
 */
@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;
    private final PersonMapper personMapper;

    @GetMapping
    List<PersonDto> getAllPerson() {
        return personService.getAllPersons();
    }

    @GetMapping("/{id}")
    PersonDto findPersonById(@PathVariable @Range Long id) {
        Person person = personService.getPersonById(id);
        return personMapper.fromPerson(person);
    }

    @PostMapping
    void addNewPerson(@RequestBody @Valid PersonDto personDto) {
        personService.addNewPerson(personDto);
    }

    @DeleteMapping("/{id}")
    void deletePersonById(@PathVariable @Range Long id) {
        personService.removePersonById(id);
    }

    @PutMapping
    void updateExistingPerson(@RequestBody @Valid PersonDto personDto) {
        personService.updatePerson(personDto);
    }

}
