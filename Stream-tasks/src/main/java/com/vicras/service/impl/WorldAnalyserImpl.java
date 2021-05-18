package com.vicras.service.impl;

import com.vicras.service.WorldAnalyser;

public class WorldAnalyserImpl implements WorldAnalyser {
    @Override
    public String getCharUsage(String line, Character character) {
        var amounts = line.codePoints()
                .filter(ch -> ch == character)
                .count();

        return "[\"" + character + "\": " + amounts + "]";
    }
}
