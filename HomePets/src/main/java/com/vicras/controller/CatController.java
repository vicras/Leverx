package com.vicras.controller;

import com.vicras.dto.CatDto;
import com.vicras.service.CatService;
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
@RequestMapping("/cat")
@RequiredArgsConstructor
public class CatController {

    private final CatService catService;

    @GetMapping
    List<CatDto> getAllCats() {
        return catService.getAll();
    }

    @GetMapping("/{id}")
    List<CatDto> findCatById(@PathVariable Long id) {
        return catService.getById(id);
    }

    @PostMapping
    void addNewCat(@RequestBody @Valid CatDto catDto) {
        catService.addCat(catDto);
    }

    @DeleteMapping("/{id}")
    void deleteCatById(@PathVariable @Range Long id) {
        catService.removeCatById(id);
    }

    @PutMapping
    void updateExistingCat(@RequestBody @Valid CatDto catDto) {
        catService.updateCat(catDto);
    }

}
