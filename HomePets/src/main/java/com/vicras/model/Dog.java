package com.vicras.model;

import com.vicras.model.enums.DogBreed;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

/**
 * @author viktar hraskou
 */
@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Dog extends Animal {

    @Column(name = "breed")
    @Enumerated(EnumType.STRING)
    private DogBreed breed;

    public Dog(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String name, Person owner, DogBreed breed) {
        super(id, createdAt, updatedAt, name, owner);
        this.breed = breed;
    }

    @Builder


    @Override
    public String seyHello() {
        return "Burk";
    }
}
