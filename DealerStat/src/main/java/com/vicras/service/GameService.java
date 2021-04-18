package com.vicras.service;

import com.vicras.entity.Game;

import java.util.List;

public interface GameService {
    List<Game> getAllGames();
    void addNewGameWithName(String name);

}
