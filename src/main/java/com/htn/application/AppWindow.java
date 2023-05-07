package com.htn.application;

import com.htn.api.view.View;
import com.htn.data.settings.Settings;
import com.htn.datastore.SettingsDataStore;
import com.htn.view.SettingsView;
import com.htn.view.ViewFactory;
import com.htn.view.bill.BillProductView;
import com.htn.view.bill.BillView;
import com.htn.view.customer.CustomerView;
import com.htn.view.main.MainView;
import com.htn.view.product.ProductView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Set;

public class AppWindow extends Application {
    @Getter private AppMenu appMenu;
    @Getter private TabPane tabPane;
    @Override
    public void start(@NotNull Stage stage) throws Exception {
        // load font
        Font.loadFont(getClass().getResource("/font/Inter-Bold.ttf").toExternalForm(), 14);
        Font.loadFont(getClass().getResource("/font/Inter-Regular.ttf").toExternalForm(), 14);
        Font.loadFont(getClass().getResource("/font/Inter-Medium.ttf").toExternalForm(), 14);

        SettingsDataStore.getInstance();
        HashMap<String, Class<? extends View>> views = ViewFactory.getViews();
        views.put("main", MainView.class);
        views.put("new bill", BillProductView.class);
        views.put("bill", BillView.class);
        views.put("customer", CustomerView.class);
        views.put("product", ProductView.class);
        views.put("settings", SettingsView.class);

        stage.setTitle("HTN Manager");
        tabPane = new TabPane();
        appMenu = new AppMenu(tabPane);

        BorderPane root = new BorderPane();

        Tab tab1 = new Tab();
        View view = ViewFactory.get("Main", tab1);
        if (view != null) {
            tab1.setContent(view.getView());
            tab1.setClosable(false);
        }

        tabPane.getTabs().add(tab1);

        root.setCenter(tabPane);
        root.setTop(appMenu);

        Scene scene = new Scene(root, 800, 500);
        scene.getStylesheets().addAll("application.css", "field.css");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {launch(args);}
}