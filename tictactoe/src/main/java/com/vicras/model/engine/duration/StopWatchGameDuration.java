package com.vicras.model.engine.duration;

import org.apache.commons.lang3.time.StopWatch;

import java.time.Duration;

public class StopWatchGameDuration  implements GameDuration{

    private final StopWatch watch = new StopWatch();
    @Override
    public void startGame() {
        watch.reset();
        watch.start();
    }

    @Override
    public void endGame() {
        pause();
    }

    @Override
    public void pause() {
        watch.stop();
    }

    @Override
    public void resume() {
        watch.resume();
    }

    @Override
    public Duration getDuration() {
        return Duration.ofNanos(watch.getNanoTime());
    }
}
