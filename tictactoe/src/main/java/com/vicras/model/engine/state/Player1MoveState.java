package com.vicras.model.engine.state;

import com.vicras.model.engine.Engine;
import com.vicras.model.engine.ai.GameAI;
import com.vicras.model.engine.move.Move;
import com.vicras.model.engine.field.Field;
import com.vicras.model.engine.field.FieldState;
import com.vicras.model.engine.field.NaughtsCrosses;

public class Player1MoveState implements GameState {

    private final Engine engine;
    private final Field field;
    private final GameAI artificialIntelligence;

    public Player1MoveState(Engine engine, Field field, GameAI artificialIntelligence) {
        this.engine = engine;
        this.field = field;
        this.artificialIntelligence = artificialIntelligence;
    }

    @Override
    public void move(Move move) {
        field.setValue(move, NaughtsCrosses.CROSSES);
        if (field.getCurrentState() == FieldState.IN_THE_PROCESS) {
            artificialIntelligence.addMove(move);
            engine.setCurrentState(engine.getPlayer2Move());
        } else {
            endGame();
        }
        engine.notifyObservers();
    }

    private void endGame() {
        engine.getGameDuration().endGame();
        engine.setCurrentState(engine.getEndGame());
    }

    @Override
    public void startGame() {

    }

    @Override
    public GameStatus getStatus() {
        return GameStatus.PLAYER_1_MOVE;
    }
}
