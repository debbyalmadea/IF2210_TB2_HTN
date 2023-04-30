package com.htn.application;

import com.htn.view.View;
import com.htn.view.ViewFactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lombok.Getter;

public class AppWindow extends Application {
    @Getter private AppMenu appMenu;
    @Getter private TabPane tabPane;
    @Override
    public void start(Stage stage) throws Exception {
        // load font
        Font.loadFont(getClass().getResource("/font/Inter-Bold.ttf").toExternalForm(), 14);
        Font.loadFont(getClass().getResource("/font/Inter-Regular.ttf").toExternalForm(), 14);
        Font.loadFont(getClass().getResource("/font/Inter-Medium.ttf").toExternalForm(), 14);


        stage.setTitle("HTN development");
        tabPane = new TabPane();
        appMenu = new AppMenu(tabPane);

        BorderPane root = new BorderPane();

        Tab tab1 = new Tab();
        View view = ViewFactory.get("main", tab1);
        if (view != null) {
            tab1.setContent(view.getView());
        }
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