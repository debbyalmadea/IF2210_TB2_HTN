package com.htn.view;

import com.htn.api.datastore.ISettingsDataStore;
import com.htn.api.datastore.SettingsObserver;
import com.htn.api.view.SettingsViewExtension;
import com.htn.api.view.View;
import com.htn.application.PluginManager;
import com.htn.datastore.SettingsDataStore;
import com.htn.data.settings.Settings;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;


import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

import javafx.util.Pair;
import org.jetbrains.annotations.Nullable;

public class SettingsView implements View, SettingsObserver {
    @Getter private final ScrollPane view;
    @Getter private final StringProperty title = new SimpleStringProperty("Settings");
    private VBox content;
    private String path;
    private ComboBox<String> extensionbox;
    private final SettingsDataStore settingsDataStore = SettingsDataStore.getInstance();
    public SettingsView() {
        view = new ScrollPane();
        view.fitToWidthProperty().set(true);
        content = new VBox();
        path = SettingsDataStore.getInstance().getSettings().getPathDir();
        init();
        view.getStylesheets().add("settings.css");
        content.setStyle("-fx-background-color: #F1F5F9;");
        view.setContent(content);
        Settings.getInstance().bind(this);
    }
    public void init() {
        content.getChildren().clear();
        content.setPadding(new Insets(32, 40, 32, 40));
        content.setSpacing(20);

        Label IOLabel = new Label("Input dan Output");
        IOLabel.getStyleClass().add("title-settings");

        // IO options n combox waow
        Text IOtitle = new Text("File extension");
        String[] extensionFormats = {".json", ".xml", ".obj"};
        extensionbox = new ComboBox<>();
        extensionbox.getItems().addAll(extensionFormats);
        extensionbox.setValue(settingsDataStore.getSettings().getFileExtension());
        extensionbox.valueProperty().addListener((observable, oldValue, newValue) -> {
            settingsDataStore.getSettings().setFileExtension(newValue);
            settingsDataStore.write();
        });

        Label save = new Label("Folder Save");
        Text text = new Text(path);
        Button buttonSelect = new Button("Select Directory");
        buttonSelect.setOnAction(e -> {
            String result = chooseDirectory();
            if (result!= null) {
                path = result;
                System.out.println(result);
                SettingsDataStore.getInstance().getSettings().setPath(path);
                settingsDataStore.write();
            }
            init();
        });

        HBox IOHBox = new HBox();
        IOHBox.setSpacing(10);
        IOHBox.setAlignment(Pos.CENTER_LEFT);
        IOHBox.getChildren().addAll(IOtitle,extensionbox);

        Label pluginLabel = new Label("Plugin");
        Image image = new Image(getClass().getResource("/image/add-plugin.png").toExternalForm());
//        File file = new File(imagePath);
//        String imageUrl = file.toURI().toString();
//        Image addImage = new Image(imageUrl);
        ImageView addPluginView = new ImageView(image);
        addPluginView.setFitWidth(20);
        addPluginView.setFitHeight(20);
        addPluginView.setOnMouseClicked(e ->{
            choosePlugin();
        });

        HBox PluginHBox = new HBox();
        PluginHBox.setSpacing(20);
        PluginHBox.setAlignment(Pos.CENTER_LEFT);
        PluginHBox.getChildren().addAll(pluginLabel,addPluginView);

        // Plugins and its path
        Pane pluginPane = getPlugin();

        content.getChildren().addAll(
                IOLabel,
                IOHBox,
                save,
                text,
                buttonSelect,
                PluginHBox,
                pluginPane
        );

        List<Object> loadedPlugins = PluginManager.getPluginsWithClass(SettingsViewExtension.class);
        loadedPlugins.forEach(plugin -> {
            SettingsViewExtension settingsView = (SettingsViewExtension) plugin;
            content.getChildren().addAll(new Label(plugin.getClass().getName() + " Settings"), settingsView.displaySetting());
        });
    }

    private void choosePlugin() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Jar File", "*.jar" ));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String pluginName = selectedFile.getName();
            String pluginPath = selectedFile.getAbsolutePath();
            settingsDataStore.getSettings().addPlugin(pluginName,pluginPath);
            settingsDataStore.write();
            content.getChildren().remove(3);
            content.getChildren().add(getPlugin());
        }
    }

    private @Nullable String chooseDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Directory");
        File selectedDirectory = directoryChooser.showDialog(null);
        if (selectedDirectory != null) {
            String directoryPath = selectedDirectory.getAbsolutePath();
            return directoryPath;
            // do something with the selected directory path
        }
        return null;
    }
    public void update(ISettingsDataStore settingsDataStore) {
        init();
    }
    private @NotNull Pane getPlugin(){

        VBox plugins = new VBox();

        for(int i = 0; i < settingsDataStore.getSettings().getPlugins().size(); i++){
            final int currentIndex = i;
//            String imagePath = "src/main/resources/image/delete-plugin.png";
            Image image = new Image(getClass().getResource("/image/delete-plugin.png").toExternalForm());
//            File file = new File(imagePath);
//            String imageUrl = file.toURI().toString();
            ImageView trashImageView = new ImageView(image);
            trashImageView.setFitWidth(20);
            trashImageView.setFitHeight(20);
            trashImageView.setOnMouseClicked(e ->{
                if (settingsDataStore.getSettings().getNumOfPlugins() > 0) {
                    settingsDataStore.getSettings().removePlugin(settingsDataStore.getSettings().getPlugins().get(currentIndex).get(0));
                    settingsDataStore.write();
                    content.getChildren().remove(3);
                    content.getChildren().add(getPlugin());
                }
            });
            // Diganti sesudah punya data dari backend buat plugin
            Label name = new Label(settingsDataStore.getSettings().getPlugins().get(i).get(0));
            name.getStyleClass().add("normal-text");
            Label path = new Label(settingsDataStore.getSettings().getPlugins().get(i).get(1));
            path.getStyleClass().add("grey-text");
            VBox pluginInfo = new VBox();
            pluginInfo.getChildren().addAll(name, path);
            pluginInfo.setMinWidth(300);
            HBox plugin = new HBox();
            plugin.setPrefHeight(60);

            plugin.getChildren().addAll(pluginInfo, trashImageView);
            plugins.getChildren().add(plugin);
        }

        return plugins;
    }

}