package com.vicras.model;

import com.vicras.dto.TaskDTO;
import com.vicras.model.enums.TaskType;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Task {
    private static int baseId = 0;

    private final String id;
    private final String title;
    private final TaskType type;
    private final LocalDate createdOn;
    private boolean done;
    private final Set<String> tags;
    private LocalDate dueTo;

    public Task(String title, TaskType type, LocalDate createdOn) {
        this.id = String.valueOf(baseId++);
        this.title = title;
        this.type = type;
        this.createdOn = createdOn;
        tags = new HashSet<>();
    }

    public TaskDTO convert2DTO(){
        return TaskDTO.builder()
                .id(id)
                .tags(tags)
                .title(title)
                .type(type)
                .createdOn(createdOn)
                .build();
    }

    public Task addTag(String tag){
        tags.add(tag);
        return this;
    }
}