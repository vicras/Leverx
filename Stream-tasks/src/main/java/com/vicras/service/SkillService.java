package com.vicras.service;

import com.vicras.model.Person;
import com.vicras.model.Skill;

import java.util.List;

public interface SkillService {
    List<Person> getBestMatchingPerson(List<Person> persons, Skill... skills);
}
