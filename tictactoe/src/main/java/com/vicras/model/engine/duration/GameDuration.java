package com.vicras.model.engine.duration;

import java.time.Duration;

public interface GameDuration {
    void startGame();
    void endGame();
    void pause();
    void resume();
    Duration getDuration();
}
