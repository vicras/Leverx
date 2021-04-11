package com.vicras.model.leaderboard.serializer;

import com.vicras.model.leaderboard.GameRecord;

import java.util.List;

public interface Serializer {
    void storeData(List<GameRecord> list);
    List<GameRecord> restoreData();
}
