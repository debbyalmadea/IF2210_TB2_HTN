package com.htn.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AppWindow extends Application {
    private AppMenu appMenu;
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("HTN development");
        appMenu = new AppMenu();

        BorderPane root = new BorderPane();
        root.setTop(appMenu);

        Scene scene = new Scene(root, 800, 500);
        scene.getStylesheets().add("application.css");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {launch(args);}
}