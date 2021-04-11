package com.vicras.model.game;

import com.vicras.model.engine.field.GameField;
import com.vicras.model.exception.PlayException;
import com.vicras.model.leaderboard.GameRecord;
import com.vicras.model.player.Player;
import com.vicras.model.player.Players;

import java.util.List;

public interface TicTacToe {
    void startGame();

    void makeMove(int i, int j, Player player) throws PlayException;

    GameField getField();

    List<GameRecord> getLeaderboard();

    Players currentPlayersOrder();

    void addListeners(TicTacToeObserver observer);

    void removeListeners(TicTacToeObserver observer);
}
