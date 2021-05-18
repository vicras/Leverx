package com.vicras;

import com.vicras.model.Person;
import com.vicras.model.Skill;
import com.vicras.model.Student;
import com.vicras.model.Task;
import com.vicras.model.enums.TaskType;
import com.vicras.service.SkillService;
import com.vicras.service.WorldAnalyser;
import com.vicras.service.impl.SkillServiceImpl;
import com.vicras.service.impl.StudentServiceImpl;
import com.vicras.service.impl.TaskServiceImpl;
import com.vicras.service.impl.WorldAnalyserImpl;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Main {

    private final StudentServiceImpl studentService;
    private final TaskServiceImpl taskService;
    private final WorldAnalyser worldAnalyser;
    private final SkillService skillService;
    private final List<Student> students;
    private final List<Person> persons;
    private final List<Task> tasks;

    public Main() {
        this.studentService = new StudentServiceImpl();
        this.taskService = new TaskServiceImpl();
        worldAnalyser = new WorldAnalyserImpl();
        skillService = new SkillServiceImpl();
        students = initStudents();
        tasks = initTasks();
        persons = initPersons();
    }

    public void outputReport() {
        String report = studentService.makeReport(students);
        System.out.println("\nStudent report:\n" + report);
    }

    public void outputAverageByMath() {
        String subject = "math";
        var average = studentService.getAverageScore(students, subject);
        System.out.println("\n" + subject + " average: " + average);
    }

    public void outputTitlesForRead() {
        String titles = taskService.getTitlesForRead(tasks, 5);
        System.out.println("\nTitles for reading");
        System.out.println(titles);
    }

    public void outputGroupingByTitle() {
        System.out.println("\nGroup by title");
        taskService.groupListByType(tasks)
                .entrySet()
                .forEach(System.out::println);
    }

    public void analyseWord() {
        String word = "alfbjanjaba t a3";

        String result1 = worldAnalyser.getCharUsage(word, 'a');
        String result2 = worldAnalyser.getCharUsage(word, 'b');
        System.out.println("\nAnalyze char usage = " + result1);
        System.out.println("\nAnalyze char usage = " + result2);
    }

    public void showBestMatchingPerson(){
        skillService.getBestMatchingPerson(persons, new Skill("English", 50), new Skill("Kannada", 50),
                new Skill("Urdu", 50), new Skill("Hindi", 50))
                .forEach(System.out::println);
    }

    private static List<Student> initStudents() {
        var student1 = new Student("student1").rate("math", 4).rate("IT", 6);
        var student2 = new Student("student2").rate("math", 7);
        return Arrays.asList(student1, student2);
    }

    private static List<Task> initTasks() {
        Task task1 = new Task("Read Version Control with Git book", TaskType.READING, LocalDate.of(2015, Month.JULY, 1)).addTag("git").addTag("reading").addTag("books");
        Task task2 = new Task("Read Java 8 Lambdas book", TaskType.READING, LocalDate.of(2015, Month.JULY, 2)).addTag("java8").addTag("reading").addTag("books");
        Task task3 = new Task("Write a mobile application to store my tasks", TaskType.CODING, LocalDate.of(2015, Month.JULY, 3)).addTag("coding").addTag("mobile");
        Task task4 = new Task("Write a blog on Java 8 Streams", TaskType.WRITING, LocalDate.of(2015, Month.JULY, 4)).addTag("blogging").addTag("writing").addTag("streams");
        Task task5 = new Task("Read Domain Driven Design book", TaskType.READING, LocalDate.of(2015, Month.JULY, 5)).addTag("ddd").addTag("books").addTag("reading");
        return Arrays.asList(task1, task2, task3, task4, task5);
    }

    private static List<Person> initPersons() {
        List<Person> persons = new ArrayList<>();

        persons.add(new Person(1L, "Lokesh", new Skill("English", 10),
                new Skill("Kannada", 20), new Skill("Hindi", 10)));

        persons.add(new Person(2L, "Mahesh", new Skill("English", 10),
                new Skill("Kannada", 30), new Skill("Hindi", 50)));

        persons.add(new Person(3L, "Ganesh", new Skill("English", 10),
                new Skill("Kannada", 20), new Skill("Hindi", 40)));

        persons.add(new Person(4L, "Ramesh", new Skill("English", 10),
                new Skill("Kannada", 20), new Skill("Hindi", 40)));

        persons.add(new Person(5L, "Suresh", new Skill("English", 10),
                new Skill("Kannada", 40), new Skill("Hindi", 40)));

        persons.add(new Person(6L, "Gnanesh", new Skill("English", 100),
                new Skill("Kannada", 20), new Skill("Hindi", 40)));

        return persons;
    }

    public static void main(String[] args) {
        Main main = new Main();

        main.outputReport();
        main.outputAverageByMath();
        main.outputReport();
        main.outputTitlesForRead();
        main.outputGroupingByTitle();
        main.analyseWord();
        main.showBestMatchingPerson();
    }

}
