package com.vicras.view;

import com.vicras.controller.GameController;

import java.util.Scanner;

public class ConsoleOutputerImpl implements ConsoleOutputer {

    private GameController controller;
    private final String helpMessage = """
            Welcome to tictactoe
            Graskov Viktor 2021 all right reserved 
            use this command words:
            game_type [-s | -m] player1 [player2] - to change game style
                -s - single player mode 
                -m - multiplayer mode 
            new_game - to start new game;
            move <cell number> - to make move 
            get_field - to get current field ( each cell has own number or marked as cross/naught)
            get_leaderboard - to get leaderboard
            help  - to get this message
            """;

    @Override
    public void init(GameController controller) {
        this.controller = controller;
        gui();
    }

    void gui() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(helpMessage);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            controller.makeQuery(line);
        }
    }

    @Override
    public void outputInfo(String info) {
        System.out.println(info);
    }

    public void outputHelp() {
        System.out.println(helpMessage);
    }
}
