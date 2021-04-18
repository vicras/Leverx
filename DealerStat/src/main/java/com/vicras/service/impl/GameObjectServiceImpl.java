package com.vicras.service.impl;

import com.vicras.dto.GameObjectDTO;
import com.vicras.entity.GameObject;
import com.vicras.repository.GameObjectRepository;
import com.vicras.service.GameObjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameObjectServiceImpl implements GameObjectService {

    final GameObjectRepository objectRepository;

    public GameObjectServiceImpl(GameObjectRepository repository) {
        this.objectRepository = repository;
    }

    @Override
    public List<GameObject> getAllGameObjects() {
        return objectRepository.findAll();
    }

    @Override
    public void addNewGameObject(GameObjectDTO gameObjectDTO) {

    }

    @Override
    public void getMyAllGameObjects() {

    }

    @Override
    public void deleteMyGameObjectWithId(long id) {

    }

    @Override
    public void updateMyGameObjectWithId(GameObjectDTO gameObjectDTO) {

    }
}
