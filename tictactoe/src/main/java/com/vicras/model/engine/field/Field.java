package com.vicras.model.engine.field;

import com.vicras.model.engine.field.checker.FieldChecker;
import com.vicras.model.engine.field.checker.WinPosition;
import com.vicras.model.engine.move.Move;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;

public abstract class Field implements GameField {

    private final int FIELD_SIZE;
    private final FieldChecker fieldChecker;
    private NaughtsCrosses[][] field;
    private FieldState fieldState;
    private WinPosition winPosition = new WinPosition();

    protected Field(int field_size, FieldChecker checker) {
        this.FIELD_SIZE = field_size;
        this.fieldChecker = checker;
        cleanField();
    }

    public void cleanField() {
        field = Stream.generate(() -> NaughtsCrosses.EMPTY)
                .limit(FIELD_SIZE)
                .map(e -> Stream.generate(() -> NaughtsCrosses.EMPTY)
                        .limit(FIELD_SIZE)
                        .toArray(NaughtsCrosses[]::new))
                .toArray(NaughtsCrosses[][]::new);
        fieldState = FieldState.IN_THE_PROCESS;
    }

    public void setValue(Move move, NaughtsCrosses state) {
        field[move.getI()][move.getJ()] = state;
        fieldState = fieldChecker.checkField(field, move);
        winPosition = fieldChecker.getWinPosition(field, move);
    }

    @Override
    public NaughtsCrosses getValue(int i, int j) {
        return field[i][j];
    }

    @Override
    public FieldState getCurrentState() {
        return fieldState;
    }

    @Override
    public int getFieldSize() {
        return FIELD_SIZE;
    }

    @Override
    public WinPosition getWinPosition() {
        return winPosition;
    }

    @Override
    public String toString() {
        StringBuilder ans = new StringBuilder();
        for(var i : field){
            for(var j : i){
                ans.append(j).append(" ");
            }
            ans.append("\n");
        }
        return ans.toString();
    }

    @Override
    public Iterator<NaughtsCrosses> iterator() {
        return Arrays.stream(field)
                .flatMap(Arrays::stream)
                .iterator();
    }
}
