package com.vicras.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Comment extends BaseEntity{

    @Column(name = "mark", nullable = false)
    private int mark;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "approved_status")
    @Enumerated(EnumType.STRING)
    private ApprovedStatus approvedStatus;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User destinationUser;

}
