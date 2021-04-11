package com.vicras.model.engine.field;

import com.vicras.model.engine.field.checker.WinPosition;

public interface GameField extends Iterable<NaughtsCrosses>{
    NaughtsCrosses getValue(int i, int j);

    FieldState getCurrentState();

    int getFieldSize();

    WinPosition getWinPosition();
}
