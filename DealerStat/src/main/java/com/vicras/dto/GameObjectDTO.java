package com.vicras.dto;

import com.vicras.entity.ApprovedStatus;
import com.vicras.entity.GameObject;
import com.vicras.entity.User;
import com.vicras.service.GameService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameObjectDTO {
    Long id;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    String title;
    String description;
    ApprovedStatus approvedStatus;
    Long ownerId;
    Set<Long> gameKeys;


    public GameObject convert2GameObject(User userOwner, GameService gameService) {
        var gameObject = GameObject.builder()
                .build();

        gameObject.setOwner(userOwner);
        setFields(gameObject, gameService);
        return gameObject;
    }

    public GameObject updateExistingObject(GameObject gameObject, GameService gameService) {
        return setFields(gameObject, gameService);
    }

    private GameObject setFields(GameObject gameObject, GameService gameService) {
        gameObject.setTitle(title);
        gameObject.setApprovedStatus(ApprovedStatus.SENT);
        gameObject.setDescription(description);
        gameObject.setGames(gameService.getGamesByKeys(gameKeys));
        return gameObject;
    }
}
