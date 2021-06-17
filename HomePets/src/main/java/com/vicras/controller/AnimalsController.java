package com.vicras.controller;

import com.vicras.dto.ExchangeDto;
import com.vicras.service.AnimalService;
import com.vicras.validator.ExchangeRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/animals")
public class AnimalsController {

    private final AnimalService animalService;
    private final ExchangeRequestValidator exchangeValidator;

    @InitBinder("exchange")
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(exchangeValidator);
    }

    @PostMapping("/exchange")
    void exchangeAnimals(@Valid @RequestBody ExchangeDto exchangeDto) {
        animalService.exchangeAnimals(exchangeDto);
    }
}
