package com.vicras.service;


import com.vicras.dto.PersonDto;

import java.util.List;

/**
 * @author viktar hraskou
 */
public interface PersonService {
    void addNewPerson(PersonDto personDto);

    void updatePerson(PersonDto personDto);

    void removePersonById(Long id);

    List<PersonDto> getAllPersons();

    List<PersonDto> getPersonById(Long id);
}
