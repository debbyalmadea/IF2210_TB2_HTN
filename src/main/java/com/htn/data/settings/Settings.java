package com.htn.data.settings;

import java.util.ArrayList;

import com.htn.api.datastore.SettingsObserver;
import com.htn.application.PluginManager;
import com.htn.datastore.SettingsDataStore;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;

import java.io.Serializable;


public class Settings implements Serializable{
    private static Settings instance = null;
    // Attributes
    @Getter
    private String fileExtension;
    private static StringProperty fileExtensionProperty = new SimpleStringProperty();
    @Getter
    private final ArrayList<ArrayList<String>> plugins;
    private static BooleanProperty changed = new SimpleBooleanProperty(false);
    // Constructor
    private Settings() {
        fileExtension = ".json";
        plugins = new ArrayList<>();
        fileExtensionProperty = new SimpleStringProperty(fileExtension);
    }
    public static synchronized Settings getInstance()
    {
        if (instance == null)
            instance = new Settings();
  
        return instance;
    }
    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
        fileExtensionProperty.set(fileExtension);
    }
    public int getNumOfPlugins() {
        return plugins.size();
    }
    public void bind(SettingsObserver observer) {
        changed.addListener((obs, oldVal, newVal) -> {
            observer.update(SettingsDataStore.getInstance());
        });
    }
    public void addPlugin(String name, String path) {
        ArrayList<String> plugin = new ArrayList<String>();
        plugin.add(name);
        plugin.add(path);
        plugins.add(plugin);
        PluginManager.load(plugin.get(1));
        changed.set(!changed.get());
    }
    public void removePlugin(String name) {
        // remove a plugin from plugins with name equal name
        for (int i = 0; i < plugins.size(); i++) {
            if (plugins.get(i).get(0).equals(name)) {
                plugins.remove(i);
                break;
            }
        }
        changed.set(!changed.get());
    }
    public String getPluginPath(String name) {
        // get the path of a plugin with name equal name
        for (int i = 0; i < plugins.size(); i++) {
            if (plugins.get(i).get(0).equals(name)) {
                return plugins.get(i).get(1);
            }
        }
        return null;
    }
}


