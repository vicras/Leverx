package com.vicras.service;

import com.vicras.entity.Game;

import java.util.List;
import java.util.Set;

public interface GameService {
    List<Game> getAllGames();

    void addNewGameWithName(String name);

    Set<Game> getGamesByKeys(Set<Long> keys);
}
