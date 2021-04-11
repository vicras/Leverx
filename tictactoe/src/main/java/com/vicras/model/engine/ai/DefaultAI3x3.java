package com.vicras.model.engine.ai;

import com.vicras.model.engine.field.Field;
import com.vicras.model.engine.field.NaughtsCrosses;
import com.vicras.model.engine.move.Move;
import com.vicras.model.engine.move.MoveImpl;

import java.util.Deque;
import java.util.LinkedList;

public class DefaultAI3x3 implements GameAI {

    private final Field field;
    private Deque<Move> moves = new LinkedList<>();
    private Move nextMove = null;

    @Override
    public void reset(){
        moves = new LinkedList<>();
        nextMove = null;
    }
    public DefaultAI3x3(Field field) {
        this.field = field;
    }

    @Override
    public Move nextMove() {
        nextMove = null;
        if(isMiddleEmpty())
            return new MoveImpl(1,1,NaughtsCrosses.CROSSES);
        checkFail();
        checkSuccess();
        if (nextMove != null)
            return nextMove;

        tryToFindCorner();
        if (nextMove != null)
            return nextMove;

        return getRandomEmpty();
    }

    private boolean isMiddleEmpty(){
        return field.getValue(1,1) == NaughtsCrosses.EMPTY;
    }

    private void checkFail() {
        Move first = moves.getFirst();
        var moveFinder = new MoveFinder(first, field);

        if (moveFinder.isFind()) {
            int i = moveFinder.getAllowableMove().getI();
            int j = moveFinder.getAllowableMove().getJ();
            nextMove = new MoveImpl(i, j, first.getSide().getOpposite());
        }
    }

    private void checkSuccess() {
        NaughtsCrosses opponentTeam = moves.getFirst().getSide();
        for (int k = 0; k < 3; k++) {
            MoveFinder finder = new MoveFinder(new MoveImpl(k, 0, opponentTeam.getOpposite()), field);

            if (finder.isFind()) {
                int i = finder.getAllowableMove().getI();
                int j = finder.getAllowableMove().getJ();
                nextMove = new MoveImpl(i, j, opponentTeam.getOpposite());
                return;
            }
        }
    }

    private void tryToFindCorner(){
        var cornerFinder = new CornerFinder(moves.getFirst(), field);
        if (cornerFinder.isFind())
            nextMove = cornerFinder.getAllowableMove();
    }

    @Override
    public void addMove(Move move) {
        moves.addFirst(move);
    }


    private Move getRandomEmpty(){
        for (int i = 0; i < field.getFieldSize(); i++) {
            for (int j = 0; j < field.getFieldSize(); j++) {
                if(field.getValue(i,j) == NaughtsCrosses.EMPTY){
                    return new MoveImpl(i,j, moves.getFirst().getSide().getOpposite());
                }
            }
        }
        return new MoveImpl(-1,-1, NaughtsCrosses.EMPTY);
    }


}
