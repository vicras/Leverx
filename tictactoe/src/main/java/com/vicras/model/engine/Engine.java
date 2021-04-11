package com.vicras.model.engine;

import com.vicras.model.engine.ai.DefaultAI3x3;
import com.vicras.model.engine.ai.GameAI;
import com.vicras.model.engine.duration.GameDuration;
import com.vicras.model.engine.duration.StopWatchGameDuration;
import com.vicras.model.engine.field.Field;
import com.vicras.model.engine.field.Field3x3;
import com.vicras.model.engine.field.GameField;
import com.vicras.model.engine.field.checker.FieldChecker3x3;
import com.vicras.model.engine.move.Move;
import com.vicras.model.engine.state.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Engine implements EngineInterface {

    private final GameState player1Move;
    private final GameState player2Move;
    private final GameState endGame;
    private GameState currentGameState;

    @Getter
    private final GameDuration gameDuration;
    private final Field field;
    private final List<EngineObserver> observers;
    private final GameAI artificialIntelligence;

    public Engine(Field field, GameDuration gameDuration, GameAI artificialIntelligence) {
        this.artificialIntelligence = artificialIntelligence;
        this.gameDuration = gameDuration;
        this.field = field;

        observers = new ArrayList<>();

        player1Move = new Player1MoveState(this, field, artificialIntelligence);
        player2Move = new Player2MoveState(this, field, artificialIntelligence);
        endGame = new EndState(this);

        currentGameState = endGame;
    }

    @Override
    public void startGame() {
        artificialIntelligence.reset();
        field.cleanField();
        currentGameState.startGame();
        gameDuration.startGame();
    }

    @Override
    public void newMove(Move move) {
        currentGameState.move(move);
    }

    @Override
    public void autoMove() {
        Move move = artificialIntelligence.nextMove();
        newMove(move);
    }

    @Override
    public GameField getField() {
        return field;
    }

    @Override
    public void addObserver(EngineObserver observer) {
        observers.add(observer);
    }

    @Override
    public boolean removeObserver(EngineObserver observer) {
        return observers.remove(observer);
    }

    public void notifyObservers() {
        observers.forEach((i) -> i.stateChanged(currentGameState.getStatus()));
    }

    //region state interface methods
    public GameState getPlayer1Move() {
        return player1Move;
    }

    public GameState getPlayer2Move() {
        return player2Move;
    }

    public GameState getEndGame() {
        return endGame;
    }

    public void setCurrentState(GameState gameState) {
        this.currentGameState = gameState;
    }

    @Override
    public GameStatus getGameStatus() {
        return currentGameState.getStatus();
    }
    //    endregion

    public static Engine defaultEngine(){
        var checker = new FieldChecker3x3();
        var field = new Field3x3(checker);
        var duration = new StopWatchGameDuration();
        var ai = new DefaultAI3x3(field);
        return new Engine(field, duration, ai);
    }
}
