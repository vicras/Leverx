package com.vicras.model.engine.ai;

import com.vicras.model.engine.field.GameField;
import com.vicras.model.engine.field.NaughtsCrosses;
import com.vicras.model.engine.move.Move;
import com.vicras.model.engine.move.MoveImpl;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

class CornerFinder {
    @Getter
    private Move allowableMove = null;
    @Getter
    private boolean isFind = false;

    private final Move opponentsLast;
    private final GameField field;

    public CornerFinder(Move opponentsLast, GameField field) {
        this.opponentsLast = opponentsLast;
        this.field = field;
        find();
    }

    private void find() {
        var emptyCorners = findEmptyCorners();
        if (!emptyCorners.isEmpty()) {
            allowableMove = sortByDistance(emptyCorners);
            isFind = true;
        }
    }

    List<Move> findEmptyCorners() {
        var emptyCorners = new ArrayList<Move>();
        if (field.getValue(0, 0) == NaughtsCrosses.EMPTY) {
            emptyCorners.add(new MoveImpl(0, 0, opponentsLast.getSide().getOpposite()));
        }
        if (field.getValue(2, 0) == NaughtsCrosses.EMPTY) {
            emptyCorners.add(new MoveImpl(2, 0, opponentsLast.getSide().getOpposite()));
        }
        if (field.getValue(0, 2) == NaughtsCrosses.EMPTY) {
            emptyCorners.add(new MoveImpl(0, 2, opponentsLast.getSide().getOpposite()));
        }
        if (field.getValue(2, 2) == NaughtsCrosses.EMPTY) {
            emptyCorners.add(new MoveImpl(2, 2, opponentsLast.getSide().getOpposite()));
        }

        return emptyCorners;
    }

    private Move sortByDistance(List<Move> moves) {
        moves.sort(this::compareMoves);
        return moves.get(0);
    }

    int compareMoves(Move a, Move b) {
        double first = getDistance(a, opponentsLast);
        double second = getDistance(b, opponentsLast);
        return Double.compare(first, second);
    }

    double getDistance(Move a, Move b) {
        int i = a.getI() - b.getI();
        int j = a.getJ() - b.getJ();

        return Math.sqrt(i * i + j * j);
    }

}
