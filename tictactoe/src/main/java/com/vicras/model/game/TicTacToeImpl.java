package com.vicras.model.game;

import com.vicras.model.engine.Engine;
import com.vicras.model.engine.EngineInterface;
import com.vicras.model.engine.EngineObserver;
import com.vicras.model.engine.field.GameField;
import com.vicras.model.engine.field.NaughtsCrosses;
import com.vicras.model.engine.move.Move;
import com.vicras.model.engine.move.MoveImpl;
import com.vicras.model.engine.state.GameStatus;
import com.vicras.model.exception.PlayException;
import com.vicras.model.leaderboard.GameRecord;
import com.vicras.model.leaderboard.Leaderboard;
import com.vicras.model.leaderboard.LeaderboardImpl;
import com.vicras.model.leaderboard.RecordImpl;
import com.vicras.model.leaderboard.serializer.SerializerImpl;
import com.vicras.model.player.Player;
import com.vicras.model.player.Players;

import java.time.LocalDateTime;
import java.util.List;

public abstract class TicTacToeImpl implements TicTacToe, EngineObserver {

    private static final String LEADERBOARD_FILE = "tictactoe.bin";;
    protected EngineInterface engine;
    protected Players players;
    protected Leaderboard leaderboard;

    protected TicTacToeImpl(Player player1, Player player2) {
        players = new Players(player1, player2);
        this.engine = Engine.defaultEngine();
        this.leaderboard = new LeaderboardImpl(new SerializerImpl(LEADERBOARD_FILE));

        engine.addObserver(this);
    }

    @Override
    public void startGame(){
        players = players.trySwapPlayers();
        engine.startGame();
    }

    @Override
    public List<GameRecord> getLeaderboard() {
        return leaderboard.getRecords();
    }

    @Override
    public GameField getField() {
        return engine.getField();
    }

    @Override
    public void addListeners(TicTacToeObserver observer) {
        engine.addObserver(observer);
    }

    @Override
    public void removeListeners(TicTacToeObserver observer) {
        engine.removeObserver(observer);
    }

    @Override
    public void stateChanged(GameStatus status) {
        if (status.equals(GameStatus.GAME_END)){
            leaderboard.addNewRecord(makeRecord());
        }
    }

    private GameRecord makeRecord() {
        var builder = RecordImpl.builder()
                .player1(players.getPlayer1())
                .player2(players.getPlayer2())
                .winner(getWinner())
                .duration(engine.getGameDuration().getDuration())
                .gameTime(LocalDateTime.now())
                .gameResult(engine.getField().getCurrentState());
        return builder.build();
    }

    private Player getWinner() {
        return switch (engine.getField().getCurrentState()) {
            case CROSSES_WIN -> players.getPlayer1();
            case NAUGHTS_WIN -> players.getPlayer2();
            default -> null;
        };
    }

    @Override
    public abstract void makeMove(int i, int j, Player player) throws PlayException;

    protected void checkPlayerValidness(Player player) throws PlayException {
        switch (engine.getGameStatus()) {
            case PLAYER_1_MOVE -> checkPlayerTurn(player, players.getPlayer1());
            case PLAYER_2_MOVE -> checkPlayerTurn(player, players.getPlayer2());
            default -> throw new PlayException("Game is over");
        }
    }

    private void checkPlayerTurn(Player provided, Player expected) throws PlayException {
        if (!provided.equals(expected)) {
            throw new PlayException("Not your turn" + provided + " " + expected);
        }
    }

    protected Move getMove(int i, int j){
        if(engine.getGameStatus() == GameStatus.PLAYER_1_MOVE){
            return new MoveImpl(i, j, NaughtsCrosses.CROSSES);
        }
        return new MoveImpl(i, j, NaughtsCrosses.NAUGHTS);
    }

    @Override
    public Players currentPlayersOrder() {
        return players;
    }
}
