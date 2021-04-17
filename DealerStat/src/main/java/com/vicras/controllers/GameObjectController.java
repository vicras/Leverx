package com.vicras.controllers;

import com.vicras.entity.GameObject;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/object")
public class GameObjectController {

    @GetMapping()
    private void getAllGameObjects(){

    }

    @PostMapping()
    private void addNewObject(@RequestBody() GameObject gameObject){

    }

    @PutMapping("/{id}")
    private void updateMyGameObject(@RequestBody() GameObject updatedGameObject, @PathVariable long id){

    }

    @DeleteMapping("/{id}")
    private void deleteMyGameObject(@PathVariable long id){

    }

    @GetMapping("/my")
    private void getAllMyObjects(){

    }

}
