package com.vicras.model;

import com.vicras.model.enums.CatBreed;
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
public class Cat extends Animal {

    @Column(name = "breed")
    @Enumerated(EnumType.STRING)
    private CatBreed breed;

    public Cat(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String name, Person owner, CatBreed breed) {
        super(id, createdAt, updatedAt, name, owner);
        this.breed = breed;
    }

    @Builder


    @Override
    public String seyHello() {
        return "Mew";
    }

}
