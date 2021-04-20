package com.vicras.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class NewUserWithCommentAndObjectsDTO {
    UserDTO user;
    List<GameObjectDTO> objectDTOS;
    CommentDTO commentDTO;
}
