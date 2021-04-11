package com.vicras.model.engine.field;

public enum NaughtsCrosses {
    NAUGHTS,
    CROSSES,
    EMPTY;

    public NaughtsCrosses getOpposite(){
        if(this == EMPTY)
            return EMPTY;
        if (this == NAUGHTS)
            return CROSSES;
        return NAUGHTS;
    }
}
