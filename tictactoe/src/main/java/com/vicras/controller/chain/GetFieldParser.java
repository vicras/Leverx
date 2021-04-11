package com.vicras.controller.chain;

import com.vicras.controller.GameControllerImpl;

public class GetFieldParser extends QueryParser{

    private final static String regex = "^get_field$";


    public GetFieldParser(GameControllerImpl controller) {
        super(controller);
    }

    @Override
    public boolean next(String line) throws ControllerException {
        if (isGetFieldCommand(line)) {
            executeCommand();
            return true;
        }
        return tryNext(line);
    }

    private void executeCommand() {
        controller.printField();
    }

    private boolean isGetFieldCommand(String line) {
        return line.matches(regex);
    }
}
