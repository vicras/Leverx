package com.vicras.controller;

import com.vicras.controller.chain.*;
import com.vicras.model.engine.field.FieldState;
import com.vicras.model.engine.field.GameField;
import com.vicras.model.engine.field.NaughtsCrosses;
import com.vicras.model.engine.state.GameStatus;
import com.vicras.model.exception.PlayException;
import com.vicras.model.game.TicTacToe;
import com.vicras.model.game.TicTacToeObserver;
import com.vicras.model.leaderboard.GameRecord;
import com.vicras.model.player.Player;
import com.vicras.model.player.Players;
import com.vicras.view.ConsoleOutputer;

import java.util.List;

public class GameControllerImpl implements GameController, TicTacToeObserver {

    private Players players;
    private TicTacToe ticTacToe;
    private ConsoleOutputer view;
    private GameType currentType;
    private QueryParser parser;
    private GameStatus currentGameStatus = GameStatus.GAME_END;

    @Override
    public void init(ConsoleOutputer view) {
        this.view = view;
        initParsers();
        view.init(this);
    }

    private void initParsers() {
        GameTypeParser gameTypeParser = new GameTypeParser  (this);
        GameHelpParser gameHelpParser = new GameHelpParser  (this);
        GameChoseChecker choseChecker = new GameChoseChecker(this);
        GetFieldParser getFieldParser = new GetFieldParser  (this);
        NewGameParser gameParser = new NewGameParser        (this);
        NewMoveParser moveParser = new NewMoveParser        (this);
        GameLeaderboardParser leaderboard = new GameLeaderboardParser(this);

        parser = gameHelpParser;
        parser.addNext(gameTypeParser);
        gameTypeParser.addNext(choseChecker);
        choseChecker.addNext(gameParser);
        gameParser.addNext(getFieldParser);
        getFieldParser.addNext(moveParser);
        moveParser.addNext(leaderboard);
    }

    @Override
    public void makeQuery(String query) {
        try {
            boolean isHandling = parser.next(query);
            if (!isHandling) {
                view.outputInfo("Unresolved query!");
            }
        } catch (ControllerException e) {
            view.outputInfo(e.getMessage());
        }
    }

    public void setType(GameType type, TicTacToe game) {
        currentType = type;
        ticTacToe = game;
        game.addListeners(this);
        view.outputInfo("Type " + currentType + " set!");
    }

    public void makeMove(int i, int j) {
        Player player = currentGameStatus == GameStatus.PLAYER_1_MOVE ? players.getPlayer1() : players.getPlayer2();
        try {
            ticTacToe.makeMove(i, j, player);
        } catch (PlayException e) {
            view.outputInfo(e.getMessage());
        }
    }

    public void printField() {
        GameField field = ticTacToe.getField();
        System.out.println(" _____________");
        for (int i = 0; i < field.getFieldSize(); i++) {
            for (int j = 0; j < field.getFieldSize(); j++) {
                NaughtsCrosses value = field.getValue(i, j);
                String symbol = getCellSymbol(value, i, j);
                System.out.print(" | " + symbol);
            }
            System.out.println(" |");
            System.out.println(" -------------");
        }

    }

    private String getCellSymbol(NaughtsCrosses value, int i, int j) {
        return switch (value) {
            case CROSSES -> "X";
            case NAUGHTS -> "O";
            case EMPTY -> String.valueOf(i * 3 + j + 1);
        };
    }

    public void outputHelp() {
        view.outputHelp();
    }

    @Override
    public void stateChanged(GameStatus status) {
        currentGameStatus = status;
        printField();
        if (status != GameStatus.GAME_END) {
            players = ticTacToe.currentPlayersOrder();
            Player player = status == GameStatus.PLAYER_1_MOVE ? players.getPlayer1() : players.getPlayer2();
            view.outputInfo(player.getName() + " turn");
        } else {
            FieldState state = ticTacToe.getField().getCurrentState();
            gameEndMessage(state);
        }

    }

    private void gameEndMessage(FieldState fieldState) {
        view.outputInfo("Game is over");
        switch (fieldState) {
            case CROSSES_WIN -> view.outputInfo("player " + players.getPlayer1() + " wins");
            case NAUGHTS_WIN -> view.outputInfo("player " + players.getPlayer2() + " wins");
            case DRAW -> view.outputInfo("Draw");
        }
    }

    public void startGame() {
        ticTacToe.startGame();
    }

    public boolean isGameChose() {
        return ticTacToe != null;
    }

    public boolean isGameInProgress() {
        return currentGameStatus != GameStatus.GAME_END;
    }

    public void printLeaderboard() {
        List<GameRecord> leaderboard = ticTacToe.getLeaderboard();
        if(leaderboard.isEmpty()){
            view.outputInfo("Leaderboard is empty");
            return;
        }
        leaderboard.forEach(e -> view.outputInfo(e.toString()));
    }
}