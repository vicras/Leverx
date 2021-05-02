package com.vicras.controllers;

import com.vicras.dto.GameObjectDTO;
import com.vicras.entity.GameObject;
import com.vicras.entity.User;
import com.vicras.service.GameObjectService;
import com.vicras.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/object")
@Transactional
public class GameObjectController {

    private final GameObjectService gameObjectService;
    private final UserService userService;

    public GameObjectController(GameObjectService gameObjectService, UserService userService) {
        this.gameObjectService = gameObjectService;
        this.userService = userService;
    }

    @GetMapping()
    private List<GameObjectDTO> getAllGameObjects() {
        return convertToDTO(gameObjectService.getAllGameObjects());
    }

    private List<GameObjectDTO> convertToDTO(List<GameObject> gameObjects) {
        return gameObjects.stream()
                .map(GameObject::convert2DTO)
                .collect(Collectors.toList());
    }

    @PostMapping()
    private ResponseEntity<String> addNewObject(@RequestBody() GameObjectDTO gameObject, Principal principal) {
        User currentUser = getUserByPrincipal(principal);
        gameObjectService.addNewGameObjectForUser(gameObject, currentUser);
        return new ResponseEntity<>("Object added successfully!", HttpStatus.OK);
    }

    @PutMapping()
    private ResponseEntity<String> updateMyGameObject(@RequestBody() GameObjectDTO updatedGameObject, Principal principal) {
        User currentUser = getUserByPrincipal(principal);
        gameObjectService.updateOrAddGameObjectForUserOwner(updatedGameObject, currentUser);
        return new ResponseEntity<>("Object updated successfully!", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteMyGameObjectById(@PathVariable long id, Principal principal) {
        User currentUser = getUserByPrincipal(principal);
        gameObjectService.deleteGameObjectForUserOwner(id, currentUser);
        return new ResponseEntity<>("Object, if it exist, deleted successfully!", HttpStatus.OK);
    }

    @GetMapping("/my")
    private List<GameObjectDTO> getAllMyObjects(Principal principal) {
        User currentUser = getUserByPrincipal(principal);
        return convertToDTO(gameObjectService.getAllGameObjectsForUser(currentUser));
    }

    private User getUserByPrincipal(Principal principal) {
        return userService.getUserByEmail(principal.getName());
    }

    @GetMapping("/for_approve")
    private List<GameObjectDTO> objectsForApprove() {
        return convertToDTO(gameObjectService.getObjectsForApprove());
    }

    @PostMapping("/approve")
    private ResponseEntity<String> approveWithIds(@RequestBody List<Long> idsToApprove) {
        gameObjectService.approveObjects(idsToApprove);
        return new ResponseEntity<>("Objects which has no [approve/decline] status successfully approved", HttpStatus.OK);
    }

    @PostMapping("/decline")
    private ResponseEntity<String> declineWithIds(@RequestBody List<Long> idsToApprove) {
        gameObjectService.declineObjects(idsToApprove);
        return new ResponseEntity<>("Objects which has no [approve/decline] status successfully declined", HttpStatus.OK);
    }

}
