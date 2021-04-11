package com.vicras.model.engine.field.checker;

import com.vicras.model.engine.field.FieldState;
import com.vicras.model.engine.field.NaughtsCrosses;
import com.vicras.model.engine.move.Move;

public class FieldChecker3x3 implements FieldChecker {

    private int length;
    private Move move;
    private NaughtsCrosses[][] field;

    public FieldChecker3x3() {
    }

    @Override
    public FieldState checkField(NaughtsCrosses[][] field, Move move) {
        this.length = field.length;
        this.field = field;
        this.move = move;

        if (isWinColumn(move.getJ()) || isWinLine(move.getI()))
            return getFieldState();

        if ((isMainDiagonal() || isAnotherDiagonal()) && (isWinMainDiagonal()|| isWinAnotherDiagonal())) {
            return getFieldState();
        }

        if(checkDraw())
            return FieldState.DRAW;

        return FieldState.IN_THE_PROCESS;
    }

    @Override
    public WinPosition getWinPosition(NaughtsCrosses[][] field, Move move) {
        this.length = field.length;
        this.field = field;
        this.move = move;

        FieldState fieldState = checkField(field, move);
        if(fieldState !=FieldState.IN_THE_PROCESS && fieldState !=FieldState.DRAW){
            var winPosition =WinPositionVariant.EMPTY;
            int line =0;
            if(isWinMainDiagonal())
                winPosition = WinPositionVariant.MAIN_DIAGONAL;
            else if(isWinAnotherDiagonal())
                winPosition =WinPositionVariant.ANOTHER_DIAGONAL;
            else if(isWinLine(move.getI())){
                winPosition =WinPositionVariant.HORIZONTAL;
                line = move.getI();
            }else if(isWinColumn(move.getJ())){
                winPosition =WinPositionVariant.VERTICAL;
                line = move.getJ();
            }

            return new WinPosition(winPosition,line);
        }
        return new WinPosition();
    }

    private boolean isWinLine(int line) {
        NaughtsCrosses state = field[line][0];
        for (int i = 0; i < length; i++) {
            if (!state.equals(field[line][i])
                    || field[line][i].equals(NaughtsCrosses.EMPTY))
                return false;
        }
        return true;
    }

    private boolean isWinColumn(int column) {
        NaughtsCrosses state = field[0][column];
        for (int i = 0; i < length; i++) {
            if (!state.equals(field[i][column])
                    || field[i][column].equals(NaughtsCrosses.EMPTY))
                return false;
        }
        return true;
    }

    private boolean isMainDiagonal() {
        return move.getI() == move.getJ();
    }

    public boolean isAnotherDiagonal() {
        return (move.getJ() == length - 1 - move.getI()) && (move.getI() == length - 1 - move.getJ());
    }

    private boolean isWinMainDiagonal() {
        NaughtsCrosses stateMain = field[0][0];
        for (int i = 0; i < length; i++) {
            if (!stateMain.equals(field[i][i]) || field[i][i].equals(NaughtsCrosses.EMPTY))
                return false;
        }
        return true;
    }
    private boolean isWinAnotherDiagonal() {
        NaughtsCrosses stateAnother = field[0][length-1];
        for (int i = 0; i < length; i++) {
            if (!stateAnother.equals(field[i][length - i - 1]) || field[i][length - i - 1].equals(NaughtsCrosses.EMPTY))
                return false;
        }
        return true;
    }

    private FieldState getFieldState() {
        if (field[move.getI()][move.getJ()] == NaughtsCrosses.NAUGHTS) return FieldState.NAUGHTS_WIN;
        if (field[move.getI()][move.getJ()] == NaughtsCrosses.CROSSES) return FieldState.CROSSES_WIN;
        return FieldState.IN_THE_PROCESS;
    }

    private boolean checkDraw(){
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if(field[i][j].equals(NaughtsCrosses.EMPTY))
                    return false;
            }
        }
        return true;
    }
}
