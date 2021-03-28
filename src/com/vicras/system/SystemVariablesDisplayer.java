package com.vicras.system;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * class provide opportunity to get all system variables
 */
public class SystemVariablesDisplayer {

    String outputFormat;

    public SystemVariablesDisplayer(String outputFormat) {
        this.outputFormat = outputFormat;
    }

    /**
     * @param variableComparator rule to compare system variables
     * @return system variables in right order and format
     */
    public String getAllSystemVariables(Comparator<String> variableComparator) {
        return System.getenv().entrySet().stream()
                .sorted(Map.Entry.comparingByKey(variableComparator))
                .map(this::mapToString)
                .collect(Collectors.joining("\n"));
    }

    private String mapToString(Map.Entry<String,String> element){
        return String.format(outputFormat, element.getKey(), element.getValue());
    }
}
