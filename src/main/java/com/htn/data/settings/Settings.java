package com.htn.data.settings;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import javafx.util.Pair;

@Getter
@Setter
public class Settings implements Serializable{
    private static Settings instance = null;

    // Attributes
    @Getter @Setter
    private String fileExtension;
    @Getter @Setter
    private ArrayList<ArrayList<String>> plugins;


    // Constructor
    private Settings() {
        fileExtension = ".json";
        plugins = new ArrayList<ArrayList<String>>();
    }

    public static synchronized Settings getInstance()
    {
        if (instance == null)
            instance = new Settings();
  
        return instance;
    }

    // Method

    public int getNumOfPlugins() {
        return plugins.size();
    }

    public void addPlugin(String name, String path) {
        ArrayList<String> plugin = new ArrayList<String>();
        plugin.add(name);
        plugin.add(path);
        plugins.add(plugin);
    }

    public void removePlugin(String name) {
        // remove a plugin from plugins with name equal name
        for (int i = 0; i < plugins.size(); i++) {
            if (plugins.get(i).get(0).equals(name)) {
                plugins.remove(i);
                break;
            }
        }

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


