package com.vicras.controller.chain;

import com.vicras.controller.GameControllerImpl;

public class GameChoseChecker extends QueryParser{

    public GameChoseChecker(GameControllerImpl controller) {
        super(controller);
    }

    @Override
    public boolean next(String line) throws ControllerException {
        if (!isGameChose()) {
            throw new ControllerException("Set game type first!");
        }
        return tryNext(line);
    }

    private boolean isGameChose() {
        return controller.isGameChose();
    }
}
