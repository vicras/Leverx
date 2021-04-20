package com.vicras.service.impl;

import com.vicras.dto.GameObjectDTO;
import com.vicras.entity.ApprovedStatus;
import com.vicras.entity.GameObject;
import com.vicras.entity.User;
import com.vicras.exception.UserNotOwnerException;
import com.vicras.repository.GameObjectRepository;
import com.vicras.service.GameObjectService;
import com.vicras.service.GameService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
            throwIfUserNotOwner(userOwner, gameObj);
            objectRepository.delete(gameObj);
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
        throwIfUserNotOwner(currentUser, oldObject);
        newObject.updateExistingObject(oldObject, gameService);
        objectRepository.save(oldObject);

    }

    private void throwIfUserNotOwner(User user, GameObject gameObject) {
        if (isOwnerGameObject(user, gameObject))
            throw new UserNotOwnerException(
                    String.format("User: %s isn't user of object with id %d", user, gameObject.getId()));
    }

    private boolean isOwnerGameObject(User user, GameObject gameObject) {
        return gameObject.getOwner().equals(user);
    }


    @Override
    public List<GameObject> getObjectsForApprove() {
        var statuses = List.of(ApprovedStatus.VIEWED,
                ApprovedStatus.SENT);
        return objectRepository.findAllByApprovedStatusIn(statuses).stream()
                .peek(e -> updateAndSaveObjectApprovedStatus(e, ApprovedStatus.VIEWED))
                .collect(Collectors.toList());
    }

    private void updateAndSaveObjectApprovedStatus(GameObject object, ApprovedStatus status) {
        object.setApprovedStatus(status);
        objectRepository.save(object);
    }

    @Override
    public void approveObjects(List<GameObjectDTO> objectsToApprove) {
        changeApprovedStatus(objectsToApprove, ApprovedStatus.APPROVED);
    }

    @Override
    public void declineObjects(List<GameObjectDTO> objectsToDecline) {
        changeApprovedStatus(objectsToDecline, ApprovedStatus.DECLINE);
    }

    private void changeApprovedStatus(List<GameObjectDTO> objectsToApprove, ApprovedStatus status) {
        List<Long> objectIds = objectsToApprove.stream()
                .map(GameObjectDTO::getId)
                .collect(Collectors.toList());
        objectRepository.updateObjectStatusWithIdIn(objectIds, status);
    }
}
