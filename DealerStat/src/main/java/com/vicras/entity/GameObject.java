package com.vicras.entity;

import com.vicras.dto.GameObjectDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true, exclude = {"games", "owner"})
@Builder
@Data
@AllArgsConstructor
@Table(name = "game_object")
@Entity
public class GameObject extends BaseEntity {
    @Column(name = "title" , nullable = false)
    private String title;

    @Column(name = "description" , nullable = false)
    private String description;

    @Column(name = "approved_status")
    @Enumerated(EnumType.STRING)
    private ApprovedStatus approvedStatus = ApprovedStatus.SENT;

    @ManyToOne()
    @JoinColumn(name = "owner_id", nullable = false )
    private User owner;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "game_object_game",
            joinColumns = { @JoinColumn(name = "game_object_id") },
            inverseJoinColumns = { @JoinColumn(name = "game_id") }
    )
    private Set<Game> games;

    public GameObject() {
    }

    public GameObjectDTO convert2DTO(){
        var keys = games.stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toSet());
        return GameObjectDTO.builder()
                .id(id)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .title(title)
                .description(description)
                .approvedStatus(approvedStatus)
                .ownerId(owner.id)
                .gameKeys(keys)
                .build();
    }
}
