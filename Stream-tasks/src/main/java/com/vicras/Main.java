package com.vicras;

import com.vicras.model.Student;
import com.vicras.model.Task;
import com.vicras.model.enums.TaskType;
import com.vicras.service.StudentService;
import com.vicras.service.TaskService;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;


public class Main {

    StudentService studentService;
    TaskService taskService;
    List<Student> students;
    List<Task> tasks;

    public Main() {
        this.studentService = new StudentService();
        this.taskService = new TaskService();
        students = initStudents();
        tasks = initTasks();
    }

    public void outputReport() {
        String report = studentService.makeReport(students);
        System.out.println("\nStudent report:\n" + report);
    }

    public void outputAverageByMath() {
        String math = "math";
        OptionalDouble it = studentService.getAverageScore(students, math);
        it.ifPresent(e -> {
            System.out.println("\n" + math + " average: " + e);
        });
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

    public static void main(String[] args) {
        Main main = new Main();

        main.outputReport();
        main.outputAverageByMath();
        main.outputReport();
        main.outputTitlesForRead();
        main.outputGroupingByTitle();
    }

}
