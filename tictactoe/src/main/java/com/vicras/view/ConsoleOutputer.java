package com.vicras.view;

import com.vicras.controller.GameController;

public interface ConsoleOutputer {
    void init(GameController controller);
    void outputInfo(String string);
    void outputHelp();
}
