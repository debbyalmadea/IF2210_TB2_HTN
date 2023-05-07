package com.htn.application;

import com.htn.api.Observer;
import com.htn.data.settings.Settings;
import com.htn.view.ViewFactory;
import javafx.scene.control.*;

public class AppMenu extends MenuBar implements Observer {
    private final TabPane tabPane;
    public AppMenu(TabPane tabPane) {
        this.tabPane = tabPane;
        update();
        Settings.getInstance().bind(this);
    }
    public void update() {
        this.getMenus().clear();
        Menu menu = new Menu("Menu");
        this.getMenus().addAll(menu);
        ViewFactory.getViews().keySet().forEach(key -> {
            System.out.println(key);
            MenuItem viewMenu = new MenuItem(key);
            viewMenu.setOnAction(e -> {
                addTab(viewMenu.getText());
            });
            menu.getItems().add(viewMenu);
        });
    }
    private void addTab(String request) {
        Tab tab = new Tab();
        tab.setContent(ViewFactory.get(request, tab).getView());
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }
}