package com.vicras.service;

import com.vicras.model.Student;

import java.util.List;

public interface StudentService {
    double getAverageScore(List<Student> students, String subject);

    String makeReport(List<Student> students);
}
