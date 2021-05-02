package com.vicras.service.impl;

import com.vicras.dto.GameObjectDTO;
import com.vicras.entity.ApprovedStatus;
import com.vicras.entity.EntityStatus;
import com.vicras.entity.GameObject;
import com.vicras.entity.User;
import com.vicras.exception.UserNotOwnerException;
import com.vicras.repository.GameObjectRepository;
import com.vicras.service.GameObjectService;
import com.vicras.service.GameService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class GameObjectServiceImpl implements GameObjectService {

    private final static Set<ApprovedStatus> NOT_APPROVED = Set.of(ApprovedStatus.SENT, ApprovedStatus.VIEWED);
    private final GameObjectRepository objectRepository;
    private final GameService gameService;

    public GameObjectServiceImpl(GameObjectRepository repository, GameService gameService) {
        this.objectRepository = repository;
        this.gameService = gameService;
    }

    @Override
    public List<GameObject> getAllGameObjects() {
        return objectRepository.findAllByEntityStatusIsAndApprovedStatusIs(EntityStatus.ACTIVE, ApprovedStatus.APPROVED);
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
            objectRepository.updateEntityStatusById(gameObj.getId(), EntityStatus.DELETED);
        });
    }

    @Override
    public void updateOrAddGameObjectForUserOwner(GameObjectDTO gameObjectDTO, User userOwner) throws UserNotOwnerException {
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
        if (!isOwnerGameObject(user, gameObject))
            throw new UserNotOwnerException(
                    String.format("User: %s isn't user of object with id %d", user, gameObject.getId()));
    }

    private boolean isOwnerGameObject(User user, GameObject gameObject) {
        return gameObject.getOwner().equals(user);
    }


    @Override
    public List<GameObject> getObjectsForApprove() {
        var approvedSt = List.of(ApprovedStatus.VIEWED,
                ApprovedStatus.SENT);
        var entitySt = List.of(EntityStatus.ACTIVE);
        return objectRepository.findAllByApprovedStatusInAndEntityStatusIn(approvedSt, entitySt).stream()
                .peek(e -> updateAndSaveObjectApprovedStatus(e, ApprovedStatus.VIEWED))
                .collect(Collectors.toList());
    }

    private void updateAndSaveObjectApprovedStatus(GameObject object, ApprovedStatus status) {
        object.setApprovedStatus(status);
        objectRepository.save(object);
    }


    @Override
    public void approveObjects(List<Long> idsToApprove) {
        objectRepository.updateObjectStatusWithIdIn(idsToApprove, ApprovedStatus.APPROVED, NOT_APPROVED);
    }

    @Override
    public void declineObjects(List<Long> idsToDecline) {
        objectRepository.updateObjectStatusWithIdIn(idsToDecline, ApprovedStatus.DECLINE, NOT_APPROVED);
    }

}
