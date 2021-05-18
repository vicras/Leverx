package com.vicras.model;


import lombok.Value;

import static java.util.Comparator.comparing;

@Value
public class Skill implements Comparable<Skill> {
    String name;
    long knownPercentage;

    @Override
    public int compareTo(Skill skill) {
        return comparing(Skill::getKnownPercentage)
                .compare(this, skill);
    }
}
