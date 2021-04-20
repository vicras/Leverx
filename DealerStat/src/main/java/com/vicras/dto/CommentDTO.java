package com.vicras.dto;

import com.vicras.entity.ApprovedStatus;
import com.vicras.entity.Comment;
import com.vicras.entity.User;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class CommentDTO {
    Long id;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    int mark;
    String message;
    ApprovedStatus approvedStatus;
    Long destinationUserId;

    public Comment convert2Comment(User destinationUser){
        return Comment.builder()
                .mark(mark)
                .message(message)
                .approvedStatus(ApprovedStatus.SENT)
                .destinationUser(destinationUser)
                .build();
    }

}
