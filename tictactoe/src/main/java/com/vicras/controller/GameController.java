package com.vicras.controller;

import com.vicras.view.ConsoleOutputer;

public interface GameController {
    void makeQuery(String querry);

    void init(ConsoleOutputer view);
}
