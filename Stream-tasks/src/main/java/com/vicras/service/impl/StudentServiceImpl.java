package com.vicras.service.impl;

import com.vicras.model.Record;
import com.vicras.model.Student;
import com.vicras.service.StudentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class StudentServiceImpl implements StudentService {

    @Override
    public double getAverageScore(List<Student> students, String subject) {
        return students.stream()
                .mapToDouble(student -> student.getRatingBySubject(subject))
                .average()
                .orElseThrow(() -> new RuntimeException("Can't count average score"));
    }

    @Override
    public String makeReport(List<Student> students) {
        var recordStream = getRecordStream(students);
        var grouped = groupRecordsBySubject(recordStream);
        return beatifyReportOutput(grouped);
    }

    static private Stream<Record> getRecordStream(List<Student> students) {
        return students.stream()
                .flatMap(student -> {
                    var rating = student.getRating().entrySet();
                    return makeRecords(student, rating);
                });
    }

    static private Stream<Record> makeRecords(Student student, Set<Map.Entry<String, Integer>> marks) {
        return marks.stream()
                .map(map -> new Record(
                                student.getName(),
                                map.getKey(),
                                map.getValue()
                        )
                );
    }

    static private HashMap<String, Set<String>> groupRecordsBySubject(Stream<Record> recordStream) {
        return recordStream
                .collect(groupingBy(
                        Record::getSubject,
                        HashMap::new,
                        mapping(StudentServiceImpl::getStudentWithMarkString, toSet()))
                );
    }

    private String beatifyReportOutput(HashMap<String, Set<String>> grouped) {
        return grouped.entrySet()
                .stream()
                .map(Object::toString)
                .collect(joining("\n"));
    }

    static private String getStudentWithMarkString(Record student) {
        return student.getName() + " : " + student.getMarks();
    }

}
