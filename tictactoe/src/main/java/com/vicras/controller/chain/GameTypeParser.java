package com.vicras.controller.chain;

import com.vicras.controller.GameControllerImpl;
import com.vicras.controller.GameType;
import com.vicras.model.game.Multiplayer;
import com.vicras.model.game.SingleGame;
import com.vicras.model.game.TicTacToe;
import com.vicras.model.player.PlayerImpl;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameTypeParser extends QueryParser {
    private final static String regex = "^game_type (-s|-m) ([\\w ]+)";
    private final static Pattern pattern = Pattern.compile(regex);

    private final String[] players = new String[2];

    public GameTypeParser(GameControllerImpl controller) {
        super(controller);
    }

    @Override
    public boolean next(String line) throws ControllerException {
        if (isGameTypeCommand(line)) {
            executeCommand(line);
            return true;
        }
        return tryNext(line);
    }

    private boolean isGameTypeCommand(String command) {
        return command.matches(regex);
    }

    private void executeCommand(String command) throws ControllerException {
        if (controller.isGameChose() && controller.isGameInProgress()){
            throw new ControllerException("Can't change type until game is end!");
        }
        GameType type = getType(command);
        setType(type);


    }

    private GameType getType(String command) throws ControllerException {
        Matcher matcher = pattern.matcher(command);
        if (matcher.find()) {
            String type = matcher.group(1);
            String[] names = Arrays.stream(matcher.group(2).strip().split(" "))
                    .filter(e-> !"".equals(e)).toArray(String[]::new);

            if("-s".equals(type)){
                parseNames(names,1);
                return GameType.SINGLEPLAYER;
            }

            parseNames(names,2);
            return GameType.MULTIPLAYER;
        }
        throw new RuntimeException("Unexpected exception!");
    }


    private void parseNames(String[] names, int playerExpected) throws ControllerException {
        if(names.length !=playerExpected)
            throw new ControllerException("Player name not found!");
        System.arraycopy(names, 0, players, 0, playerExpected);
    }

    private void setType(GameType type) {
        TicTacToe game;
        if(type == GameType.SINGLEPLAYER){
            game = new SingleGame(new PlayerImpl(players[0]));
        }else{
            game = new Multiplayer(new PlayerImpl(players[0]), new PlayerImpl(players[1]));
        }
        controller.setType(type, game);
    }

}
