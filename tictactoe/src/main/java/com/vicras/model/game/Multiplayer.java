package com.vicras.model.game;

import com.vicras.model.engine.move.Move;
import com.vicras.model.engine.state.GameStatus;
import com.vicras.model.exception.PlayException;
import com.vicras.model.player.Player;
import com.vicras.model.player.Players;
import lombok.NonNull;

public class Multiplayer extends TicTacToeImpl {
    public Multiplayer(Player player1, Player player2) {
        super(player1, player2);
    }

    @Override
    public void makeMove(int i, int j, Player player) throws PlayException {
        checkPlayerValidness(player);
        Move move = getMove(i, j);
        engine.newMove(move);
    }

    public void changePlayers(@NonNull Player player1, @NonNull Player player2) throws PlayException {
        if (engine.getGameStatus() == GameStatus.GAME_END) {
            players = new Players(player1, player2);
        } else throw new PlayException("You can't change players, before game will end");
    }
}
