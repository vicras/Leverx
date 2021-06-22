package com.vicras.mapper;

import com.vicras.dto.DogDto;
import com.vicras.model.Dog;
import com.vicras.model.Person;
import com.vicras.service.PersonService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author viktar hraskou
 */
@Mapper(componentModel = "spring")
public abstract class DogMapper {

    @Autowired
    private PersonService personService;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(source = "owner", target = "owner", qualifiedByName = "findOwner")
    public abstract Dog toDog(DogDto dogDto);

    @Mapping(target = "owner", source = "dog.owner.id")
    public abstract DogDto fromDog(Dog dog);

    @Named("findOwner")
    public Person mapToOwner(Long owner) {
        return personService.getPersonById(owner);
    }
}
