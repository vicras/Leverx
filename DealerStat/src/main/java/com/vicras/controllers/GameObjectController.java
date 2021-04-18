package com.vicras.controllers;

import com.vicras.dto.GameObjectDTO;
import com.vicras.entity.GameObject;
import com.vicras.service.GameObjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/object")
public class GameObjectController {

    private final GameObjectService gameObjectService;

    public GameObjectController(GameObjectService gameObjectService) {
        this.gameObjectService = gameObjectService;
    }

    @GetMapping()
    private List<GameObjectDTO> getAllGameObjects(){
        return gameObjectService.getAllGameObjects()
                .stream()
                .map(GameObject::convert2DTO)
                .collect(Collectors.toList());
    }

    @PostMapping()
    private void addNewObject(@RequestBody() GameObject gameObject){

    }

    @PutMapping()
    private void updateMyGameObject(@RequestBody() GameObject updatedGameObject){

    }

    @DeleteMapping("/{id}")
    private void deleteMyGameObject(@PathVariable long id){

    }

    @GetMapping("/my")
    private void getAllMyObjects(){

    }

}
