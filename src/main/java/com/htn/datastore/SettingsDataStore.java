package com.htn.datastore;

import com.google.gson.reflect.TypeToken;
import com.htn.application.AppWindow;
import com.htn.data.settings.Settings;
// to be deleted
import com.htn.data.item.Item;
import com.htn.datastore.utils.IDataWriter;
import com.htn.datastore.utils.IFileReader;
import com.htn.datastore.utils.JSONUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class SettingsDataStore {
    @Getter
    private Settings settings;
    private static SettingsDataStore instance = null;
    @Getter @Setter
    private String file = "settings.json";

    private SettingsDataStore() {
        read();
    }

    public static SettingsDataStore getInstance() {
        if (instance == null) {
            instance = new SettingsDataStore();
        }
        return instance;
    }
    
    public void read() {
        Type type = new TypeToken<Settings>() {}.getType();
        try {
            IFileReader JSONreader = new JSONUtil(type);
            Object result = JSONreader.readFile(file);
            settings = (Settings) result;
        } catch (IOException e) {
            settings = Settings.getInstance();
        }
    }

    public void write() {
        Type type = new TypeToken<Settings>() {}.getType();
        try {
            IDataWriter JSONwriter = new JSONUtil(type);
            JSONwriter.writeData(file, settings);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(Settings settings) {
        this.settings = settings;
        write();
    }

}
