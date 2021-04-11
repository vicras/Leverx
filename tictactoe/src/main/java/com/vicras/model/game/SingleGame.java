package com.vicras.model.game;

import com.vicras.model.engine.move.Move;
import com.vicras.model.engine.state.GameStatus;
import com.vicras.model.exception.PlayException;
import com.vicras.model.player.Player;
import com.vicras.model.player.PlayerImpl;
import com.vicras.model.player.Players;
import lombok.NonNull;

public class SingleGame extends TicTacToeImpl {

    private static final Player AI_PLAYER = new PlayerImpl("Computer");
    private GameStatus currentState;

    public SingleGame(Player player1) {
        super(player1, AI_PLAYER);
    }

    @Override
    public void makeMove(int i, int j, Player player) throws PlayException {
        checkPlayerValidness(player);
        Move move = getMove(i, j);
        engine.newMove(move);
        if( currentState != GameStatus.GAME_END){
            engine.autoMove();
        }
    }

    @Override
    public void startGame() {
        super.startGame();
        if(players.getPlayer1().equals(AI_PLAYER)){
            engine.autoMove();
        }
    }

    public void changePlayers(@NonNull Player player1) throws PlayException {
        if (engine.getGameStatus() == GameStatus.GAME_END) {
            players = new Players(player1, AI_PLAYER);
        } else throw new PlayException("You can't change players, before game will end");
    }

    @Override
    public void stateChanged(GameStatus status) {
        super.stateChanged(status);
        currentState = status;
    }

}
