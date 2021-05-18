package com.vicras.service.impl;

import com.vicras.model.DecorationPerson;
import com.vicras.model.Person;
import com.vicras.model.Skill;
import com.vicras.service.SkillService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class SkillServiceImpl implements SkillService {

    @Override
    public List<Person> getBestMatchingPerson(List<Person> persons, Skill... skills) {
        return Arrays.stream(skills)
                .map(skillToFind -> {
                    var stub = new DecorationPerson(
                            new Person(0L, "null", new Skill(skillToFind.getName(),
                                    0))
                    );
                    List<DecorationPerson> peoples = convert2ProxyPerson(persons);
                    return getReducePerson(peoples, stub, skillToFind);
                })
                .map(DecorationPerson::getPerson)
                .collect(Collectors.toList());
    }

    private DecorationPerson getReducePerson(List<DecorationPerson> decorationPeoples, DecorationPerson stub, Skill skillToFind) {
        return decorationPeoples.stream()
                .reduce(
                        stub,
                        (identPerson, currentPerson) -> getPersonAfterComparing(skillToFind, identPerson, currentPerson)
                );
    }

    private DecorationPerson getPersonAfterComparing(Skill i, DecorationPerson identPerson, DecorationPerson currentPerson) {
        return isSupportPerson(identPerson, currentPerson, i) ? currentPerson : identPerson;
    }

    private boolean isSupportPerson(DecorationPerson identity, DecorationPerson current, Skill skill) {
        if (current.isChose())
            return false;

        Optional<Skill> currentPersonSkill = findSkill(current, skill);
        if (currentPersonSkill.isPresent()) {
            Skill identityPersonSkill = findSkill(identity, skill).get();
            return isBetter(identity, current, currentPersonSkill, identityPersonSkill);
        }
        return false;
    }

    private boolean isBetter(DecorationPerson identity, DecorationPerson current, Optional<Skill> currentPersonSkill, Skill identityPersonSkill) {
        if (identityPersonSkill.compareTo(currentPersonSkill.get()) < +0) {
            current.setChose(true);
            identity.setChose(false);
            return true;
        }
        return false;
    }

    private Optional<Skill> findSkill(DecorationPerson person, Skill toFind) {
        return person.getSkills().stream()
                .filter(skill -> skill.getName().equals(toFind.getName()))
                .findFirst();
    }

    private List<DecorationPerson> convert2ProxyPerson(List<Person> list) {
        return list.stream()
                .map(DecorationPerson::new)
                .collect(toList());
    }


}
