package com.vicras.service.impl;

import com.vicras.dto.GameObjectDTO;
import com.vicras.entity.GameObject;
import com.vicras.entity.User;
import com.vicras.exception.UserNotOwnerException;
import com.vicras.repository.GameObjectRepository;
import com.vicras.service.GameObjectService;
import com.vicras.service.GameService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class GameObjectServiceImpl implements GameObjectService {

    final GameObjectRepository objectRepository;
    final GameService gameService;

    public GameObjectServiceImpl(GameObjectRepository repository, GameService gameService) {
        this.objectRepository = repository;
        this.gameService = gameService;
    }

    @Override
    public List<GameObject> getAllGameObjects() {
        return objectRepository.findAll();
    }

    @Override
    public List<GameObject> getAllGameObjectsForUser(User user) {
        return objectRepository.findAllByOwner(user);
    }

    @Override
    public void addNewGameObjectForUser(GameObjectDTO gameObjectDTO, User user) {
        GameObject gameObject = gameObjectDTO.convert2GameObject(user, gameService);
        objectRepository.save(gameObject);
    }

    @Override
    public void deleteGameObjectForUserOwner(long id, User userOwner) {
        objectRepository.findById(id).ifPresent(gameObj -> {
            if (isOwnerGameObject(userOwner, gameObj)) {
                objectRepository.delete(gameObj);
            } else {
                throw new UserNotOwnerException(
                        String.format("User: %s isn't user of object with id %d", userOwner, id));
            }
        });
    }

    @Override
    public void updateGameObjectForUserOwner(GameObjectDTO gameObjectDTO, User userOwner) {
        Objects.requireNonNull(gameObjectDTO.getId(), () -> "Game object id must be not null");

        objectRepository.findById(gameObjectDTO.getId())
                .ifPresentOrElse(
                        gameObject -> updateIfOwnerObject(userOwner, gameObject, gameObjectDTO),
                        () -> addNewGameObjectForUser(gameObjectDTO, userOwner)
                );
    }

    private void updateIfOwnerObject(User currentUser, GameObject oldObject, GameObjectDTO newObject) {
        if (isOwnerGameObject(currentUser, oldObject)) {
            newObject.updateExistingObject(oldObject, gameService);
            objectRepository.save(oldObject);
        } else {
            throw new UserNotOwnerException(
                    String.format("User: %s isn't user of object with id %d", currentUser, oldObject.getId()));
        }
    }

    private boolean isOwnerGameObject(User user, GameObject gameObject) {
        return gameObject.getOwner().equals(user);
    }
}
