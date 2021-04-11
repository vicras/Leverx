package com.vicras.model.engine;

import com.vicras.model.engine.state.GameStatus;

public interface EngineObserver {
    void stateChanged(GameStatus status);
}
