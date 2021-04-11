package com.vicras.model.engine.move;

import com.vicras.model.engine.field.NaughtsCrosses;

public interface Move {
    int getI();
    int getJ();
    NaughtsCrosses getSide();
}
