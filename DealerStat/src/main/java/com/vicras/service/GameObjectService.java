package com.vicras.service;

import com.vicras.dto.GameObjectDTO;
import com.vicras.entity.GameObject;
import com.vicras.entity.User;
import com.vicras.exception.UserNotOwnerException;

import java.util.List;

public interface GameObjectService {
    List<GameObject> getAllGameObjects();

    List<GameObject> getAllGameObjectsForUser(User user);

    void addNewGameObjectForUser(GameObjectDTO gameObjectDTO, User user);

    void deleteGameObjectForUserOwner(long id, User userOwner) throws UserNotOwnerException;

    void updateOrAddGameObjectForUserOwner(GameObjectDTO gameObjectDTO, User userOwner) throws UserNotOwnerException;

    List<GameObject> getObjectsForApprove();

    void approveObjects(List<Long> idsToApprove);

    void declineObjects(List<Long> idsToDecline);

}
