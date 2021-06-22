package com.vicras.service;

import com.vicras.dto.DogDto;

import java.util.List;

/**
 * @author viktar hraskou
 */
public interface DogService {

    List<DogDto> getAll();

    DogDto getById(Long id);

    void updateDog(DogDto dogDto);

    void addDog(DogDto dogDto);

    void removeDogById(Long id);

}
