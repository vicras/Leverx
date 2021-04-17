package com.vicras.controllers;

import com.vicras.entity.Game;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
public class GameController {

    @GetMapping()
    private void getAllGames(){

    }

    @PostMapping()
    private void addNewGame(@RequestBody Game game){
    }

}
