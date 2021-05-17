package com.vicras.model;

import lombok.Data;
import lombok.Value;

import java.util.HashMap;
import java.util.Map;

@Data
public class Student {

    @Value
    public static class Record {
        String name;
        String subject;
        Integer marks;
    }

    Map<String, Integer> rating;
    String name;

    public Student(String name) {
        rating = new HashMap<>();
        this.name = name;
    }

    public Student rate(String subject, Integer rate) {
        rating.put(subject, rate);
        return this;
    }


}