package com.htn.application;

import com.htn.api.view.View;
import com.htn.view.ViewFactory;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;

public class AppMenu extends MenuBar {
    private TabPane tabPane;
    public AppMenu(TabPane tabPane) {
        this.tabPane = tabPane;
        final Menu menu = new Menu("Menu");
        this.getMenus().addAll(menu);
//        List<Object> plugins = PluginManager.getInstance().getPlugin(View.class);

        ViewFactory.getViews().keySet().forEach(key -> {
            System.out.println(key);
            MenuItem viewMenu = new MenuItem(key);
            viewMenu.setOnAction(e -> {
                addTab(viewMenu.getText());
            });
            menu.getItems().add(viewMenu);
        });

//        Menu subMenu = new Menu("New tab");
//        MenuItem customerMenu = new MenuItem("Customer");
//        MenuItem mainMenu = new MenuItem("Main");
//        MenuItem billMenu = new MenuItem("Bill");
//        MenuItem productMenu = new MenuItem("Product");
//        MenuItem billProductMenu = new MenuItem("Shopping");
//
//
//        customerMenu.setOnAction(e -> {
//            addTab(customerMenu.getText());
//        });
//        mainMenu.setOnAction(e->{
//            addTab(mainMenu.getText());
//        });
//        billMenu.setOnAction(e->{
//            addTab(billMenu.getText());
//        });
//        billProductMenu.setOnAction(e -> {
//            addTab(billProductMenu.getText());
//        });
//        productMenu.setOnAction(e -> addTab(productMenu.getText()));
//
//        subMenu.getItems().add(mainMenu);
//        subMenu.getItems().add(customerMenu);
//        subMenu.getItems().add(billMenu);
//        subMenu.getItems().add(productMenu);
//        subMenu.getItems().add(billProductMenu);
//
//        List<Object> plugins = PluginManager.getInstance().getPlugin(View.class);
//        plugins.forEach(plugin -> {
//
//                    View view = (View) plugin;
//                    MenuItem pluginMenu = new MenuItem(view.getTitle().get());
//                    subMenu.getItems().add(pluginMenu);
//                    pluginMenu.setOnAction(e -> {
//                        addTab(pluginMenu.getText());
//                    });
//        });
//
//        SeparatorMenuItem separator = new SeparatorMenuItem();
//        MenuItem setting = new MenuItem("Settings...");
//        setting.setOnAction(e -> {
//            addTab((setting.getText()));
//        });
//        menu.getItems().add(subMenu);
//        menu.getItems().add(separator);
//        menu.getItems().add(setting);
    }
    // TODO! TEMPORARY FUNCTION, WILL BE REPLACED LATER!
    private void addTab(String request) {
        Tab tab = new Tab();
        tab.setContent(ViewFactory.get(request, tab).getView());
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }
}