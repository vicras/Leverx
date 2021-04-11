package com.vicras.model.engine.state;

import com.vicras.model.engine.move.Move;

public interface GameState {

    void move(Move move);
    void startGame();
    GameStatus getStatus();
}
