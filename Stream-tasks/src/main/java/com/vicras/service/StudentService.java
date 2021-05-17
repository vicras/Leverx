package com.vicras.service;

import com.vicras.model.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StudentService {

    public OptionalDouble getAverageScore(List<Student> students, String subject) {
        return students.stream()
                .map(e -> e.getRating().get(subject))
                .filter(Objects::nonNull)
                .mapToDouble(e -> e)
                .average();
    }

    public String makeReport(List<Student> students) {
        var recordStream = getRecordStream(students);
        return groupRecordsBySubject(recordStream).entrySet()
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));
    }

    static private Stream<Student.Record> getRecordStream(List<Student> students) {
        return students.stream()
                .flatMap(student -> {
                    var rating = student.getRating().entrySet();
                    return makeRecords(student, rating);
                });
    }

    static private Stream<Student.Record> makeRecords(Student student, Set<Map.Entry<String, Integer>> marks) {
        return marks.stream()
                .map(map -> new Student.Record(student.getName(), map.getKey(), map.getValue()));
    }

    static private HashMap<String, Set<String>> groupRecordsBySubject(Stream<Student.Record> recordStream) {
        return recordStream
                .collect(Collectors.groupingBy(
                        Student.Record::getSubject,
                        HashMap::new,
                        Collectors.mapping(StudentService::getStudentWithMarkString, Collectors.toSet()))
                );
    }

    static private String getStudentWithMarkString(Student.Record student) {
        return student.getName() + " : " + student.getMarks();
    }

}
