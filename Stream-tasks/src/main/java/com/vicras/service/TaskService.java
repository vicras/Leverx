package com.vicras.service;

import com.vicras.model.Task;
import com.vicras.model.enums.TaskType;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TaskService {
    Comparator<Task> creationDateComparator = Comparator.comparing(Task::getCreatedOn);
    Function<Task, TaskType> typeMapping = Task::getType;
    Function<Task, String> titleMapping = Task::getTitle;


    public String getTitlesForRead(List<Task> tasks, int amounts) {
        if (amounts < 0)
            throw new RuntimeException("amounts should be positive");

        return tasks.stream()
                .sorted(creationDateComparator)
                .filter(e -> e.getType() == TaskType.READING)
                .limit(amounts)
                .map(Task::getTitle)
                .collect(Collectors.joining(", "));
    }

    public Map<TaskType, List<Task>> groupListByType(List<Task> tasks) {
        return (Map<TaskType, List<Task>>) getCollect(tasks, typeMapping);
    }

    public Map<String, List<Task>> groupListByTitle(List<Task> tasks) {
        return (Map<String, List<Task>>) getCollect(tasks, titleMapping);
    }

    private Map<?, List<Task>> getCollect(List<Task> tasks, Function<Task, ?> mapper) {
        return tasks.stream()
                .collect(Collectors.groupingBy(mapper));
    }
}
