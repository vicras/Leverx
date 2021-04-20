package com.vicras.controllers;

import com.vicras.dto.GameObjectDTO;
import com.vicras.entity.GameObject;
import com.vicras.entity.User;
import com.vicras.service.GameObjectService;
import com.vicras.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/object")
public class GameObjectController {

    private final GameObjectService gameObjectService;
    private final UserService userService;

    public GameObjectController(GameObjectService gameObjectService, UserService userService) {
        this.gameObjectService = gameObjectService;
        this.userService = userService;
    }

    @GetMapping()
    private List<GameObjectDTO> getAllGameObjects(){
        return gameObjectService.getAllGameObjects()
                .stream()
                .map(GameObject::convert2DTO)
                .collect(Collectors.toList());
    }

    @PostMapping()
    private ResponseEntity<String> addNewObject(@RequestBody() GameObjectDTO gameObject, Principal principal){
        User currentUser = userService.getUserByEmail(principal.getName());
        gameObjectService.addNewGameObjectForUser(gameObject, currentUser);
        return new ResponseEntity<>("Object added successfully!", HttpStatus.OK);
    }

    @PutMapping()
    private ResponseEntity<String> updateMyGameObject(@RequestBody() GameObjectDTO updatedGameObject, Principal principal){
        User currentUser = userService.getUserByEmail(principal.getName());
        gameObjectService.updateGameObjectForUserOwner(updatedGameObject, currentUser);
        return new ResponseEntity<>("Object updated successfully!", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteMyGameObjectById(@PathVariable long id, Principal principal){
        User currentUser = userService.getUserByEmail(principal.getName());
        gameObjectService.deleteGameObjectForUserOwner(id, currentUser);
        return new ResponseEntity<>("Object deleted successfully!", HttpStatus.OK);
    }

    @GetMapping("/my")
    private void getAllMyObjects(Principal principal){
        User currentUser = userService.getUserByEmail(principal.getName());
        gameObjectService.getAllGameObjectsForUser(currentUser);
    }

    @GetMapping("/for_approve")
    private List<GameObjectDTO> objectsForApprove(){
        return gameObjectService.getObjectsForApprove().stream()
                .map(GameObject::convert2DTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/approve")
    private ResponseEntity<String> approveWithIds(@RequestBody List<Long> idsToApprove){
        gameObjectService.approveObjects(idsToApprove);
        return new ResponseEntity<>("Successfully approved", HttpStatus.OK);
    }

    @PostMapping("/decline")
    private ResponseEntity<String> declineWithIds(@RequestBody List<Long> idsToApprove){
        gameObjectService.declineObjects(idsToApprove);
        return new ResponseEntity<>("Successfully declined", HttpStatus.OK);
    }

}
