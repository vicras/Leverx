package com.vicras.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.InheritanceType.TABLE_PER_CLASS;


/**
 * @author viktar hraskou
 */
@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Inheritance(strategy = TABLE_PER_CLASS)
public abstract class Animal extends BaseEntity {

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = LAZY)
    private Person owner;

    public Animal(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String name, Person owner) {
        super(id, createdAt, updatedAt);
        this.name = name;
        this.owner = owner;
    }

    public abstract String seyHello();

}
