package com.vicras.mapper;

import com.vicras.dto.CatDto;
import com.vicras.exception.EntityNotFoundException;
import com.vicras.model.Cat;
import com.vicras.model.Person;
import com.vicras.repository.PersonRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author viktar hraskou
 */
@Mapper(componentModel = "spring")
public abstract class CatMapper {

    @Autowired
    private PersonRepository personRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(source = "owner", target = "owner", qualifiedByName = "findOwner")
    public abstract Cat toCat(CatDto catDto);

    @Mapping(target = "owner", source = "cat.owner.id")
    public abstract CatDto fromCat(Cat cat);

    @Named("findOwner")
    public Person mapToOwner(Long owner) {
        return personRepository.findById(owner)
                .orElseThrow(() -> new EntityNotFoundException(Person.class, owner));
    }

}
