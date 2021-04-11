package com.vicras.model.engine.ai;

import com.vicras.model.engine.move.Move;

public interface GameAI {
    void reset();
    Move nextMove();
    void addMove(Move move);
}
