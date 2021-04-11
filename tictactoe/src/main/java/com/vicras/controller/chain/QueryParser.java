package com.vicras.controller.chain;

import com.vicras.controller.GameControllerImpl;

public abstract class QueryParser {

    protected final GameControllerImpl controller;
    QueryParser next;

    public QueryParser(GameControllerImpl controller) {
        this.controller = controller;

    }

    public void addNext(QueryParser parser){
        next = parser;
    }

    boolean tryNext(String line) throws ControllerException {
        if (next == null) {
            return false;
        }
        return next.next(line);
    }

    public abstract boolean next(String line) throws ControllerException;


}
