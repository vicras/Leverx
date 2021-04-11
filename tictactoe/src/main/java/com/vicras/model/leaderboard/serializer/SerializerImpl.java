package com.vicras.model.leaderboard.serializer;

import com.vicras.model.leaderboard.GameRecord;

import java.io.*;
import java.nio.file.FileSystems;
import java.util.Collections;
import java.util.List;

public class SerializerImpl implements Serializer {

    private static final String separator = FileSystems.getDefault().getSeparator();
    private static final String HOME_FOLDER_NAME = ".tictactoe";
    private static final String SAVE_FOLDER_NAME = "save";

    private final String directoryPath = HOME_FOLDER_NAME + separator + SAVE_FOLDER_NAME + separator;
    private final File homeDirectory = new File(System.getProperty("user.home"));
    private final File fileDirectory = new File(homeDirectory + separator + directoryPath);
    private final File file;

    public SerializerImpl(String file) {
        this.file  = new File(fileDirectory, file);
        createDirectoryAndFile();
    }

    @Override
    public void storeData(List<GameRecord> list) {
        try (var out = new FileOutputStream(file);
                ObjectOutputStream encode = new ObjectOutputStream(out)) {
            encode.writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<GameRecord> restoreData() {
        try (var in = new FileInputStream(file);
                ObjectInputStream decode = new ObjectInputStream(in)) {
            return (List<GameRecord>) decode.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }


    private void createDirectoryAndFile() {
        this.fileDirectory.mkdirs();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
