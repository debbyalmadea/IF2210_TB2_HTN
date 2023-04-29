package com.htn.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AppWindow extends Application {
    private AppMenu appMenu;
    private TabPane tabPane;
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("HTN development");
        appMenu = new AppMenu();
        tabPane = new TabPane();

        BorderPane root = new BorderPane();

        Tab tab1 = new Tab("Planes", new Label("Show all planes available"));
        Tab tab2 = new Tab("Cars"  , new Label("Show all cars available"));
        Tab tab3 = new Tab("Boats" , new Label("Show all boats available"));

        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab2);
        tabPane.getTabs().add(tab3);

        root.setCenter(tabPane);
        root.setTop(appMenu);

        Scene scene = new Scene(root, 800, 500);
        scene.getStylesheets().add("application.css");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {launch(args);}
}