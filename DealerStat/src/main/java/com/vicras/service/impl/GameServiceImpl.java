package com.vicras.service.impl;

import com.vicras.entity.Game;
import com.vicras.repository.GameRepository;
import com.vicras.service.GameService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class GameServiceImpl implements GameService {

    final GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    @Transactional
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @Override
    public void addNewGameWithName(String name) {
        var game = Game.builder()
                .title(name)
                .build();
        gameRepository.save(game);
    }

    @Override
    public Set<Game> getGamesByKeys(Set<Long> keys) {
        return new HashSet<>(gameRepository.findAllById(keys));
    }
}
