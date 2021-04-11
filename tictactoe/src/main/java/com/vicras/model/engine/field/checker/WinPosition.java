package com.vicras.model.engine.field.checker;

public class WinPosition {
    final private WinPositionVariant positionVariant;
    final private boolean isExist;
    final private int line;

    public WinPosition() {
        isExist = false;
        line = 0;
        positionVariant = WinPositionVariant.EMPTY;
    }

    public WinPosition(WinPositionVariant positionVariant, int line) {
        this.positionVariant = positionVariant;
        this.isExist = true;
        this.line = line;
    }

    public WinPositionVariant getPositionVariant() {
        return positionVariant;
    }

    public boolean isExist() {
        return isExist;
    }

    public int getLine() {
        return line;
    }

}
