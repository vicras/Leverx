package com.vicras.model.engine.duration;

import java.time.Duration;
import java.time.Instant;

public class GameDurationImpl implements GameDuration {
    private Instant start;
    private Instant end;
    private Instant pauseStart;
    private Instant pauseEnd;
    private Duration pauseTime;
    private boolean inPause = false;

    public GameDurationImpl() {
    }

    private void reset() {
        start = null;
        end = null;
        pauseEnd = null;
        pauseStart = null;
        inPause = false;
        pauseTime = Duration.ofSeconds(0);
    }

    @Override
    public void startGame() {
        reset();
        start = Instant.now();
    }

    @Override
    public void endGame() {
        resume();
        end = Instant.now();
    }

    @Override
    public void pause() {
        inPause = true;
        pauseStart = Instant.now();
    }

    @Override
    public void resume() {
        if (inPause) {
            pauseEnd = Instant.now();
            pauseTime = pauseTime.plus(Duration.between(pauseStart, pauseEnd));
            inPause = false;
        }
    }

    @Override
    public Duration getDuration() {
        endGame();

        if (end != null && start != null) {
            return Duration.between(start, end).minus(pauseTime);
        }

        return Duration.ZERO;
    }

}
