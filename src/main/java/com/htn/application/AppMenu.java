package com.htn.application;

import com.htn.api.datastore.SettingsObserver;
import com.htn.api.datastore.ISettingsDataStore;
import com.htn.api.view.View;
import com.htn.data.settings.Settings;
import com.htn.view.ViewFactory;
import javafx.scene.control.*;

public class AppMenu extends MenuBar implements SettingsObserver {
    private final TabPane tabPane;
    public AppMenu(TabPane tabPane) {
        this.tabPane = tabPane;
        Settings.getInstance().bind(this);
        init();
    }
    public void init() {
        this.getMenus().clear();
        Menu menu = new Menu("Menu");
        this.getMenus().addAll(menu);
        ViewFactory.getViews().keySet().forEach(key -> {
            MenuItem viewMenu = new MenuItem(key);
            viewMenu.setOnAction(e -> {
                addTab(viewMenu.getText());
            });
            menu.getItems().add(viewMenu);
        });
    }
    public void update(ISettingsDataStore test) {
        init();
    }
    private void addTab(String request) {
        Tab tab = new Tab();
        View view = ViewFactory.get(request, tab);
        if (view != null) {
            tab.setContent(view.getView());
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);
        }
    }
}