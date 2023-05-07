package com.htn.datastore;

import com.google.gson.reflect.TypeToken;
import com.htn.api.datastore.ISettingsDataStore;
import com.htn.application.AppWindow;
import com.htn.application.PluginManager;
import com.htn.data.settings.Settings;
// to be deleted
import com.htn.data.item.Item;
import com.htn.datastore.utils.IDataWriter;
import com.htn.datastore.utils.IFileReader;
import com.htn.datastore.utils.IOUtilFactory;
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

public class SettingsDataStore implements ISettingsDataStore {
    @Getter
    private Settings settings;
    private static SettingsDataStore instance = null;
    @Getter @Setter
    private String file = "settings";
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
            IFileReader reader = IOUtilFactory.getReader(Settings.getInstance().getFileExtension(), type);
            Object result = reader.readFile(file + Settings.getInstance().getFileExtension());
            settings = (Settings) result;
            settings.getPlugins().forEach(plugin -> PluginManager.load(plugin.get(1)));
        } catch (IOException e) {
            settings = Settings.getInstance();
        }
    }
    public void write() {
        Type type = new TypeToken<Settings>() {}.getType();
        try {
            IDataWriter writer = IOUtilFactory.getWriter(Settings.getInstance().getFileExtension(), type);
            if (writer != null) writer.writeData(file + Settings.getInstance().getFileExtension(), settings);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
