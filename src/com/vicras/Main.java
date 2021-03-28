package com.vicras;

import com.vicras.date.DateTimeProcessor;
import com.vicras.date.DateTimeProvider;
import com.vicras.system.SystemVariablesDisplayer;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Program input class
 */
public class Main {

    private final DateTimeProvider clock;
    private final SystemVariablesDisplayer displayer;

    public Main() {
        clock = new DateTimeProcessor(
                DateTimeFormatter.ISO_DATE,
                DateTimeFormatter.ISO_TIME);
        displayer = new SystemVariablesDisplayer("|%30s | %s");
    }

    /**
     * use this method to output current variable info
     */
    public void outputVariableInfo() {
        String lineDelimiter = getLineDelimiter(80);
        String vars = displayer.getAllSystemVariables(Comparator.naturalOrder());

        System.out.println("Date: " + clock.getCurrentDate());
        System.out.println("Time: " + clock.getCurrentTime());
        System.out.println(lineDelimiter);
        System.out.println("System variables list:");
        System.out.println(vars);
        System.out.println(lineDelimiter);
    }

    /**
     * @param amount symbols amounts
     * @return line that consist amount of "*"
     */
    private String getLineDelimiter(int amount) {
        return Stream.generate(() -> "*")
                .limit(amount)
                .collect(Collectors.joining());
    }

    /**
     * Program input point
     */
    public static void main(String[] args) {
        Main main = new Main();
        main.outputVariableInfo();
    }
}
