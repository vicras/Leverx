package com.vicras;

import com.vicras.controller.GameController;
import com.vicras.controller.GameControllerImpl;
import com.vicras.view.ConsoleOutputer;
import com.vicras.view.ConsoleOutputerImpl;

public class Main {

    public static void main(String[] args){
        start();
    }

    static private void start() {
        ConsoleOutputer view = new ConsoleOutputerImpl();
        GameController controller = new GameControllerImpl();
        controller.init(view);
    }
}
