package com.vicras.model.leaderboard;

import com.vicras.model.engine.field.FieldState;
import com.vicras.model.player.Player;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

public interface GameRecord extends Serializable     {
    Player getPlayer1();
    Player getPlayer2();
    Duration getDuration();
    LocalDateTime getGameTime();
    Player getWinner();
    FieldState getGameResult();
}
