package com.vicras.exception;

import com.vicras.model.Animal;

import static java.lang.String.format;

/**
 * @author viktar hraskou
 */
public class AnimalsNotBelongException extends RuntimeException {

    public AnimalsNotBelongException(Animal animal, Long id) {
        super(format("Animal %s with id='%d' not belong to person with id='%d'", animal.getName(), animal.getId(), id));
    }

}
