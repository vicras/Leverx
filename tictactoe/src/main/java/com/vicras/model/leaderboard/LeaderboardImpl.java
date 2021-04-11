package com.vicras.model.leaderboard;

import com.vicras.model.leaderboard.serializer.Serializer;

import java.util.Collections;
import java.util.List;

public class LeaderboardImpl implements Leaderboard {

    private final List<GameRecord> list;
    private final Serializer serializer;

    public LeaderboardImpl(Serializer serializer) {
        this.serializer = serializer;
        list = restoreRecords();
    }

    @Override
    public void addNewRecord(GameRecord record) {
        list.add(record);
        storeRecords(list);
    }

    @Override
    public List<GameRecord> getRecords() {
        return Collections.unmodifiableList(list);
    }

    private List<GameRecord> restoreRecords() {
        return serializer.restoreData();
    }

    private void storeRecords(List<GameRecord> list) {
        serializer.storeData(list);
    }
    
}
