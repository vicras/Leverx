package com.vicras.model.engine.move;

import com.vicras.model.engine.field.NaughtsCrosses;

public class MoveImpl implements Move {

    private final int i;
    private final int j;
    private final NaughtsCrosses naughtsCrosses;

    public MoveImpl(int i, int j, NaughtsCrosses naughtsCrosses) {
        this.naughtsCrosses = naughtsCrosses;
        this.i = i;
        this.j = j;
    }

    @Override
    public int getI() {
        return i;
    }

    @Override
    public int getJ() {
        return j;
    }

    @Override
    public NaughtsCrosses getSide() {
        return naughtsCrosses;
    }
}
