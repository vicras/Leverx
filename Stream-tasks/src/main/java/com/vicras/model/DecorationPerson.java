package com.vicras.model;

import lombok.Data;

import java.util.List;

@Data
public class DecorationPerson {

    private boolean isChose;
    private Person person;

    public DecorationPerson(Person person) {
        this.person = person;
    }

    public List<Skill> getSkills(){
        return person.getSkills();
    }

}
