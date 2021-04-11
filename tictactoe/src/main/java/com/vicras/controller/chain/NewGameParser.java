package com.vicras.controller.chain;

import com.vicras.controller.GameControllerImpl;

public class NewGameParser extends QueryParser{
    private final static String regex = "^new_game$";

    public NewGameParser(GameControllerImpl controller) {
        super(controller);
    }

    @Override
    public boolean next(String line) throws ControllerException {
        if (isNewGameCommand(line)) {
            executeCommand();
            return true;
        }
        return tryNext(line);
    }

    private void executeCommand() {
        controller.startGame();
    }

    private boolean isNewGameCommand(String line) {
        return line.matches(regex);
    }
}
