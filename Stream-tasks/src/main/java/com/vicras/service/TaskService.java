package com.vicras.service;

import com.vicras.dto.TaskDTO;
import com.vicras.model.Task;
import com.vicras.model.enums.TaskType;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface TaskService {
    String getTitlesForRead(List<Task> tasks, int amounts);

    Map<String, List<Task>> groupListByTitle(List<Task> tasks);

    Map<TaskType, List<Task>> groupListByType(List<Task> tasks);

    Collection<TaskDTO> map2DTOCollection(Collection<Task> collection);
}
