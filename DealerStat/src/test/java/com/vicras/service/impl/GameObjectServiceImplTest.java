package com.vicras.service.impl;

import com.vicras.dto.GameObjectDTO;
import com.vicras.entity.EntityStatus;
import com.vicras.entity.GameObject;
import com.vicras.entity.User;
import com.vicras.exception.UserNotOwnerException;
import com.vicras.repository.GameObjectRepository;
import com.vicras.service.GameService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class GameObjectServiceImplTest {

    @Mock
    GameObjectRepository gameObjectRepository;
    @Mock
    GameService gameService;

    @InjectMocks
    GameObjectServiceImpl gameObjectService;


    @Test
    void getAllGameObjectsForNullUser() {
        gameObjectService.getAllGameObjectsForUser(null);
    }

    @Test
    void deleteGameObjectForUserOwner() {
        var user = new User();
        var gameObj = new GameObject();
        gameObj.setOwner(user);
        Mockito.doReturn(Optional.of(gameObj))
                .when(gameObjectRepository)
                .findById(ArgumentMatchers.eq(1L));

        gameObjectService.deleteGameObjectForUserOwner(1L, user);
        Mockito.verify(gameObjectRepository, Mockito.times(1))
                .updateEntityStatusById(
                        ArgumentMatchers.eq(gameObj.getId()),
                        ArgumentMatchers.eq(EntityStatus.DELETED)
                );
    }

    @Test
    void deleteGameObjectForUserNotOwner() {
        var user = new User();
        var owner = new User();
        owner.setFirstName("name");
        var gameObj = new GameObject();
        gameObj.setOwner(owner);
        Mockito.doReturn(Optional.of(gameObj))
                .when(gameObjectRepository)
                .findById(ArgumentMatchers.eq(1L));


        Assertions.assertThrows(
                UserNotOwnerException.class,
                () -> gameObjectService.deleteGameObjectForUserOwner(1L, user)
        );
    }

    @Test
    void updateGameObjectForUserOwner() {
        var gameObjectDto = new GameObjectDTO();
        gameObjectDto.setId(1L);
        var owner = new User();
        var gameObj = new GameObject();
        gameObj.setOwner(owner);
        Mockito.doReturn(Optional.of(gameObj))
                .when(gameObjectRepository)
                .findById(gameObjectDto.getId());

        gameObjectService.updateGameObjectForUserOwner(gameObjectDto,owner);
        Mockito.verify(gameObjectRepository, Mockito.times(1))
                .save(ArgumentMatchers.any());
    }


}