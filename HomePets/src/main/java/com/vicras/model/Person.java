package com.vicras.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

import static javax.persistence.CascadeType.ALL;

/**
 * @author viktar hraskou
 */
@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Person extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "birthday")
    private LocalDate birthday;

    @OneToMany(mappedBy = "owner", cascade = ALL)
    private Collection<Animal> animals;

    @Builder
    public Person(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String name, LocalDate birthday, Collection<Animal> animals) {
        super(id, createdAt, updatedAt);
        this.name = name;
        this.birthday = birthday;
        this.animals = animals;
    }

}
