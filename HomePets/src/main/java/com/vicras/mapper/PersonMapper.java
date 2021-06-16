package com.vicras.mapper;

import com.vicras.dto.PersonDto;
import com.vicras.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author viktar hraskou
 */
@Mapper(componentModel = "spring")
public interface PersonMapper {

    DogMapper INSTANCE = Mappers.getMapper(DogMapper.class);

    PersonDto fromPerson(Person person);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Person toPerson(PersonDto personDto);

}
