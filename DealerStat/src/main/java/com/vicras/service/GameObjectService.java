package com.vicras.service;

import com.vicras.dto.GameObjectDTO;
import com.vicras.entity.GameObject;
import com.vicras.entity.User;

import java.util.List;

public interface GameObjectService {
    List<GameObject> getAllGameObjects();

    List<GameObject> getAllGameObjectsForUser(User user);

    public void addNewGameObjectForUser(GameObjectDTO gameObjectDTO, User user);

    void deleteGameObjectForUserOwner(long id, User userOwner);

    public void updateGameObjectForUserOwner(GameObjectDTO gameObjectDTO, User userOwner);

}
