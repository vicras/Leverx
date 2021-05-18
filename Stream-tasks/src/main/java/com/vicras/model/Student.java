package com.vicras.model;

import lombok.Data;
import lombok.Value;

import java.util.HashMap;
import java.util.Map;

@Data
public class Student {

    private Map<String, Integer> rating;
    private String name;

    public Student(String name) {
        rating = new HashMap<>();
        this.name = name;
    }

    public Student rate(String subject, Integer rate) {
        rating.put(subject, rate);
        return this;
    }

    public Integer getRatingBySubject(String subject){
        return rating.get(subject);
    }


}