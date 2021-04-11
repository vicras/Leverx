package com.vicras.controller.chain;

import com.vicras.controller.GameControllerImpl;

public class GameLeaderboardParser extends QueryParser{

    private final static String regex = "^get_leaderboard$";

    public GameLeaderboardParser(GameControllerImpl controller) {
        super(controller);
    }

    @Override
    public boolean next(String line) throws ControllerException {
        if (isLeaderboardCommand(line)) {
            executeCommand();
            return true;
        }
        return tryNext(line);
    }

    private void executeCommand() {
        controller.printLeaderboard();
    }

    private boolean isLeaderboardCommand(String line) {
        return line.matches(regex);
    }
}
