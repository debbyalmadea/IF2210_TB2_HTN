package com.htn.view;

import com.htn.api.view.View;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class SettingsView implements View {
    @Getter private final ScrollPane view;
    @Getter private final StringProperty title = new SimpleStringProperty("Settings");
    private VBox content;
    @Getter private final Tab parent;
    private ComboBox<String> extensionbox;

    public SettingsView(Tab parent) {
        view = new ScrollPane();
        view.fitToWidthProperty().set(true);
        this.parent = parent;
        init();
        content.getStylesheets().add("settings.css");
        view.setContent(content);
    }

    public void init() {
        content = new VBox();
        content.setPadding(new Insets(32, 40, 32, 40));
        content.setSpacing(20);

        Label IOLabel = new Label("Input dan Output");
        IOLabel.getStyleClass().add("title-settings");

        // IO options n combox waow
        Text IOtitle = new Text("File extension");
        String[] extensionFormats = {".json", ".xml", ".obj"};
        extensionbox = new ComboBox<>();
        extensionbox.getItems().addAll(extensionFormats);

        HBox IOHBox = new HBox();
        IOHBox.setSpacing(10);
        IOHBox.setAlignment(Pos.CENTER_LEFT);
        IOHBox.getChildren().addAll(IOtitle,extensionbox);

        Label pluginLabel = new Label("Plugin");

        String imagePath = "src/main/resources/image/add-plugin.png";
        File file = new File(imagePath);
        String imageUrl = file.toURI().toString();
        Image trashImage = new Image(imageUrl);
        ImageView addPluginView = new ImageView(trashImage);
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
                PluginHBox,
                pluginPane
        );
    }

    private void choosePlugin() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Jar File", "*.jar" ));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            // Perform action with the selected file
        }
    }

    private @NotNull Pane getPlugin(){
        String[] plugin1 = {"Chart Plugin 1", "root/java/plugins"};
        String[] plugin2 = {"Chart Plugin 2", "root/java/plugins"};

        String[][] pluginsData = {plugin1,plugin2};

        VBox plugins = new VBox();

        for(String[] element : pluginsData){
            String imagePath = "src/main/resources/image/delete-plugin.png";
            File file = new File(imagePath);
            String imageUrl = file.toURI().toString();
            Image trashImage = new Image(imageUrl);
            ImageView trashImageView = new ImageView(trashImage);
            trashImageView.setFitWidth(20);
            trashImageView.setFitHeight(20);
            // Diganti sesudah punya data dari backend buat plugin
            Label name = new Label(element[0]);
            name.getStyleClass().add("normal-text");
            Label path = new Label(element[1]);
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