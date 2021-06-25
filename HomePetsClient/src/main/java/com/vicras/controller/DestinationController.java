package com.vicras.controller;


import com.vicras.service.DestinationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author viktar hraskou
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/destination")
public class DestinationController {

    private final DestinationService destinationService;

    @GetMapping("/persons")
    public ResponseEntity<String> getAllPersons(){
        return destinationService.getAllPersons();
    }

    @GetMapping("/dogs")
    public ResponseEntity<String> getAllDogs(){
        return destinationService.getAllDogs();
    }

    @GetMapping("/cats")
    public ResponseEntity<String> getAllCats(){
        return destinationService.getAllCats();
    }
}
