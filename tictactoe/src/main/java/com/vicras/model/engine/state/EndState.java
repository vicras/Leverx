package com.vicras.model.engine.state;

import com.vicras.model.engine.Engine;
import com.vicras.model.engine.move.Move;

public class EndState implements GameState {

    private final Engine engine;

    public EndState(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void move(Move move) {
    }

    @Override
    public void startGame() {
        engine.setCurrentState(engine.getPlayer1Move());
        engine.notifyObservers();
    }

    @Override
    public GameStatus getStatus() {
        return GameStatus.GAME_END;
    }
}
