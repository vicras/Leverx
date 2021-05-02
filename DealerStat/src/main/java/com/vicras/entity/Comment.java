package com.vicras.entity;

import com.vicras.dto.CommentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@Data
@Entity
public class Comment extends BaseEntity {

    @Column(name = "mark", nullable = false)
    private int mark;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "approved_status")
    @Enumerated(EnumType.STRING)
    private ApprovedStatus approvedStatus = ApprovedStatus.SENT;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User destinationUser;

    public CommentDTO convert2DTO() {
        return CommentDTO.builder()
                .id(id)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .mark(mark)
                .message(message)
                .approvedStatus(approvedStatus)
                .destinationUserId(destinationUser.getId())
                .build();
    }

    public Comment() {

    }
}
