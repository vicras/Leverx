package com.vicras.mappers;

import com.vicras.dto.TaskDTO;
import com.vicras.model.Task;

public interface ToTaskDTOMapper {
    TaskDTO map2DTO(Task task);

    static TaskDTO convert2TaskDTO(Task task){
        return TaskDTO.builder()
                .id(task.getId())
                .tags(task.getTags())
                .title(task.getTitle())
                .type(task.getType())
                .createdOn(task.getCreatedOn())
                .build();
    }
}
