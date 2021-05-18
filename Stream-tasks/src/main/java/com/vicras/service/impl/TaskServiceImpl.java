package com.vicras.service.impl;

import com.vicras.dto.TaskDTO;
import com.vicras.mappers.ToTaskDTOMapper;
import com.vicras.model.Task;
import com.vicras.model.enums.TaskType;
import com.vicras.service.TaskService;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.vicras.model.enums.TaskType.READING;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class TaskServiceImpl implements TaskService {

    ToTaskDTOMapper taskDTOMapper;

    public TaskServiceImpl() {
        this.taskDTOMapper = ToTaskDTOMapper::convert2TaskDTO;
    }

    @Override
    public String getTitlesForRead(List<Task> tasks, int amounts) {
        if (amounts < 0)
            throw new RuntimeException("amounts should be positive");

        return tasks.stream()
                .filter(task -> READING.equals(task.getType()))
                .sorted(comparing(Task::getCreatedOn))
                .limit(amounts)
                .map(Task::getTitle)
                .collect(joining(", "));
    }

    @Override
    public Map<TaskType, List<Task>> groupListByType(List<Task> tasks) {
        return (Map<TaskType, List<Task>>) getCollect(tasks, Task::getType);
    }

    @Override
    public Map<String, List<Task>> groupListByTitle(List<Task> tasks) {
        return (Map<String, List<Task>>) getCollect(tasks, Task::getTitle);
    }

    private Map<?, List<Task>> getCollect(List<Task> tasks, Function<Task, ?> mapper) {
        return tasks.stream()
                .collect(groupingBy(mapper));
    }

    @Override
    public Collection<TaskDTO> map2DTOCollection(Collection<Task> collection) {
        return collection.stream()
                .map(taskDTOMapper::map2DTO)
                .collect(toList());
    }
}
