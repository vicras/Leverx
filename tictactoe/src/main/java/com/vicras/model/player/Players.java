package com.vicras.model.player;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.Random;

@Value
@AllArgsConstructor
public class Players {
    private Player player1;
    private Player player2;

    public Players swapPlayers() {
        return new Players(player2, player1);
    }

    public Players trySwapPlayers() {
        if (new Random().nextBoolean())
            return swapPlayers();
        return this;
    }

}
