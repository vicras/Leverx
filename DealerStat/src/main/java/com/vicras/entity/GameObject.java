package com.vicras.entity;

import com.vicras.dto.GameObjectDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "game_object")
@Entity
public class GameObject extends BaseEntity {
    @Column(name = "title" , nullable = false)
    private String title;

    @Column(name = "description" , nullable = false)
    private String description;

    @Column(name = "approved_status")
    @Enumerated(EnumType.STRING)
    private ApprovedStatus approvedStatus;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "game_object_game",
            joinColumns = { @JoinColumn(name = "game_object_id") },
            inverseJoinColumns = { @JoinColumn(name = "game_id") }
    )
    private Set<Game> games;

    public GameObjectDTO convert2DTO(){
        return GameObjectDTO.builder()
                .id(id)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .title(title)
                .description(description)
                .approvedStatus(approvedStatus.name())
                .ownerId(owner.id)
                .build();
    }
}
