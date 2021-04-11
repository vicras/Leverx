package com.vicras.controller.chain;

import com.vicras.controller.GameControllerImpl;

public class GameHelpParser extends QueryParser{
    private final static String regex = "^help$";


    public GameHelpParser(GameControllerImpl controller) {
        super(controller);
    }

    @Override
    public boolean next(String line) throws ControllerException {
        if (isHelpCommand(line)) {
            executeCommand();
            return true;
        }
        return tryNext(line);
    }

    private void executeCommand() {
        controller.outputHelp();
    }

    private boolean isHelpCommand(String line) {
        return line.matches(regex);
    }
}
