package com.vicras.model.leaderboard;

import java.util.List;

public interface Leaderboard {
    void addNewRecord(GameRecord record);
    List<GameRecord> getRecords();
}
