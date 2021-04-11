package com.vicras.model.engine;

import com.vicras.model.engine.duration.GameDuration;
import com.vicras.model.engine.field.GameField;
import com.vicras.model.engine.move.Move;
import com.vicras.model.engine.state.GameStatus;

public interface EngineInterface {
    void startGame();

    void newMove(Move move);

    void autoMove();

    GameDuration getGameDuration();

    GameField getField();

    GameStatus getGameStatus();

    void addObserver(EngineObserver observer);

    boolean removeObserver(EngineObserver observer);

}
