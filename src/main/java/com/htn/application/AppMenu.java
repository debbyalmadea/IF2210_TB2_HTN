package com.htn.application;

import com.htn.view.ViewFactory;
import javafx.scene.control.*;

public class AppMenu extends MenuBar {
    private TabPane tabPane;
    public AppMenu(TabPane tabPane) {
        this.tabPane = tabPane;
        final Menu menu = new Menu("Menu");
        this.getMenus().addAll(menu);

        Menu subMenu = new Menu("New tab");
        MenuItem customerMenu = new MenuItem("Customer");
        customerMenu.setOnAction(e -> {
            addTab(customerMenu.getText());
        });

        subMenu.getItems().add(customerMenu);

        SeparatorMenuItem separator = new SeparatorMenuItem();
        MenuItem setting = new MenuItem("Settings...");
        setting.setOnAction(e -> {
            addTab((setting.getText()));
        });
        menu.getItems().add(subMenu);
        menu.getItems().add(separator);
        menu.getItems().add(setting);
    }
    // TODO! TEMPORARY FUNCTION, WILL BE REPLACED LATER!
    private void addTab(String request) {
        Tab tab = new Tab();
        tab.setContent(ViewFactory.get(request, tab).getView());
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }
}