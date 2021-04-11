package com.vicras.model.engine.ai;

import com.vicras.model.engine.field.GameField;
import com.vicras.model.engine.field.NaughtsCrosses;
import com.vicras.model.engine.move.Move;
import com.vicras.model.engine.move.MoveImpl;
import lombok.Getter;

class MoveFinder {
    @Getter
    private Move allowableMove = null;
    @Getter
    private boolean isFind = false;
    final private Move opponentsLast;
    final private GameField field;

    public MoveFinder(Move opponentsLast, GameField field) {
        this.opponentsLast = opponentsLast;
        this.field = field;
        check();
    }

    private void check() {
        if (isNearFail())
            isFind = true;
    }

    boolean isNearFail() {
        int amounts = 0;
        for (int k = 0; k < field.getFieldSize(); k++) {
            if (isOpponentsCell(k, opponentsLast.getJ())) {
                amounts++;
            }
        }
        if (amounts == 2 && allowableMove != null)
            return true;

        allowableMove = null;
        amounts = 0;
        for (int k = 0; k < field.getFieldSize(); k++) {
            if (isOpponentsCell(opponentsLast.getI(), k)) {
                amounts++;
            }
        }
        if (amounts == 2 && allowableMove != null)
            return true;

        allowableMove = null;
        amounts = 0;
        for (int k = 0; k < field.getFieldSize(); k++) {
            if (isOpponentsCell(k, k)) {
                amounts++;
            }
        }
        if (amounts == 2 && allowableMove != null)
            return true;

        allowableMove = null;
        amounts = 0;
        for (int k = 0; k < field.getFieldSize(); k++) {
            if (isOpponentsCell(k, field.getFieldSize() - 1 - k)) {
                amounts++;
            }
        }
        if (amounts == 2 && allowableMove != null)
            return true;
        return false;
    }

    private boolean isOpponentsCell(int i, int j) {
        if (field.getValue(i, j) == NaughtsCrosses.EMPTY) {
            allowableMove = new MoveImpl(i, j, NaughtsCrosses.EMPTY);
        }

        if (field.getValue(i, j) == opponentsLast.getSide()) {
            return true;
        }

        return false;
    }

}
