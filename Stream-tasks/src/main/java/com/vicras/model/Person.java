package com.vicras.model;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class Person {
    private Long personId;

    private String name;

    private List<Skill> skills;

    public Person(Long personId, String name, Skill... skills) {
        this.personId = personId;
        this.name = name;
        this.skills = Arrays.asList(skills);
    }

    @Override
    public String toString() {
        return name;
    }
}
