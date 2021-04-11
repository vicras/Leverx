package com.vicras.model.engine.field.checker;

import com.vicras.model.engine.move.Move;
import com.vicras.model.engine.field.FieldState;
import com.vicras.model.engine.field.NaughtsCrosses;

public interface FieldChecker {
    FieldState checkField(NaughtsCrosses[][] field, Move move);
    WinPosition getWinPosition(NaughtsCrosses[][] field, Move move);
}
