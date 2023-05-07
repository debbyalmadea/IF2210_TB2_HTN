package com.htn.application;

import com.htn.api.view.View;
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

import java.util.HashMap;

public class AppWindow extends Application {
    @Getter private AppMenu appMenu;
    @Getter private TabPane tabPane;
    @Override
    public void start(@NotNull Stage stage) throws Exception {
        // load font
        Font.loadFont(getClass().getResource("/font/Inter-Bold.ttf").toExternalForm(), 14);
        Font.loadFont(getClass().getResource("/font/Inter-Regular.ttf").toExternalForm(), 14);
        Font.loadFont(getClass().getResource("/font/Inter-Medium.ttf").toExternalForm(), 14);

        HashMap<String, Class<? extends View>> views = ViewFactory.getViews();
        views.put("Main", MainView.class);
        views.put("New Bill", BillProductView.class);
        views.put("Bill", BillView.class);
        views.put("Customer", CustomerView.class);
        views.put("Product", ProductView.class);
        views.put("Settings", SettingsView.class);

        PluginManager.load("/Users/almadeaputri/Documents/IF2210_TB2_HTN/out/artifacts/chartplugin_jar/chartplugin.jar");
        PluginManager.load("/Users/almadeaputri/Documents/IF2210_TB2_HTN/out/artifacts/piechartplugin_jar/piechartplugin.jar");

        stage.setTitle("HTN development");
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