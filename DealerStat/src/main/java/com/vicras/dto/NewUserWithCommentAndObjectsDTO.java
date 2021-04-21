package com.vicras.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewUserWithCommentAndObjectsDTO {
    UserDTO user;
    List<GameObjectDTO> objectDTOS;
    CommentDTO commentDTO;
}
