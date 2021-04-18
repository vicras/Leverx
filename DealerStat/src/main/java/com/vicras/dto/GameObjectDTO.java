package com.vicras.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class GameObjectDTO {
    Long id;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    String title;
    String description;
    String approvedStatus;
    Long ownerId;
}
