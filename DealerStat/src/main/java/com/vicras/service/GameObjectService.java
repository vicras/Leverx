package com.vicras.service;

import com.vicras.dto.GameObjectDTO;
import com.vicras.entity.GameObject;

import java.util.List;

public interface GameObjectService {
    List<GameObject> getAllGameObjects();
    void addNewGameObject(GameObjectDTO gameObjectDTO);
    void getMyAllGameObjects();
    void deleteMyGameObjectWithId(long id);
    void updateMyGameObjectWithId(GameObjectDTO gameObjectDTO);

}
