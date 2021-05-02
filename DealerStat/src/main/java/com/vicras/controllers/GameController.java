package com.vicras.controllers;

import com.vicras.dto.GameDTO;
import com.vicras.entity.Game;
import com.vicras.service.GameService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/game")
public class GameController {

    final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping()
    private List<GameDTO> getAllGames() {
        return gameService.getAllGames()
                .stream()
                .map(Game::convert2DTO)
                .collect(Collectors.toList());
    }

    @PostMapping()
    private void addNewGame(@RequestBody GameDTO gameDTO) {
        gameService.addNewGameWithName(gameDTO.getTitle());
    }

}
