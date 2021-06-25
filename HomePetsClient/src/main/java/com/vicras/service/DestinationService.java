package com.vicras.service;

import org.springframework.http.ResponseEntity;

/**
 * @author viktar hraskou
 */
public interface DestinationService {

    ResponseEntity<String> getAllPersons();

    ResponseEntity<String> getAllDogs();

    ResponseEntity<String> getAllCats();

}
