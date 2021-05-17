package com.vicras.dto;

import com.vicras.model.enums.TaskType;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Set;

@Builder
public class TaskDTO {
    private final String id;
    private final String title;
    private final TaskType type;
    private final LocalDate createdOn;
    private final Set<String> tags;

    public TaskDTO(String id, String title, TaskType type, LocalDate createdOn, Set<String> tags) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.createdOn = createdOn;
        this.tags = tags;
    }
}