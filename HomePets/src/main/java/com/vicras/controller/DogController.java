package com.vicras.controller;

import com.vicras.dto.DogDto;
import com.vicras.service.DogService;
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
@RequestMapping("/dog")
@RequiredArgsConstructor
public class DogController {

    private final DogService dogService;

    @GetMapping
    List<DogDto> getAllDogs() {
        return dogService.getAll();
    }

    @GetMapping("/{id}")
    List<DogDto> findDogById(@PathVariable Long id) {
        return dogService.getById(id);
    }

    @PostMapping
    void addNewDog(@RequestBody @Valid DogDto dogDto) {
        dogService.addDog(dogDto);
    }

    @DeleteMapping("/{id}")
    void deleteDogById(@PathVariable @Range Long id) {
        dogService.removeDogById(id);
    }

    @PutMapping
    void updateExistingDog(@RequestBody @Valid DogDto dogDto) {
        dogService.updateDog(dogDto);
    }

}
