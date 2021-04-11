package com.vicras.controller.chain;

import com.vicras.controller.GameControllerImpl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewMoveParser extends QueryParser {

    private final static String regex = "^move (\\d+)$";
    private final static Pattern pattern = Pattern.compile(regex);


    public NewMoveParser(GameControllerImpl controller) {
        super(controller);
    }

    @Override
    public boolean next(String line) throws ControllerException {
        if (isNewMoveCommand(line)) {
            executeCommand(line);
            return true;
        }
        return tryNext(line);
    }

    private void executeCommand(String line) throws ControllerException {
        if(!controller.isGameInProgress()){
            throw new ControllerException("Start game first!");
        }
        int position = parseCell(line) - 1;
        controller.makeMove(position / 3, position % 3);
    }

    private int parseCell(String line) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            String group = matcher.group(1);
            return Integer.parseInt(group);
        }
        throw new RuntimeException("Unexpected state");
    }

    private boolean isNewMoveCommand(String line) {
        return line.matches(regex);
    }


}
