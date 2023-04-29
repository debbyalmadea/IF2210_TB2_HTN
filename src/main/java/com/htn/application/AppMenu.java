package com.htn.application;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

public class AppMenu extends MenuBar {
    public AppMenu() {
        final Menu menu = new Menu("Menu");
        this.getMenus().addAll(menu);

        Menu subMenu = new Menu("New tab");
        MenuItem menuItem11 = new MenuItem("Halaman bill");
        subMenu.getItems().add(menuItem11);
        SeparatorMenuItem separator = new SeparatorMenuItem();
        MenuItem setting = new MenuItem("Settings...");

        menu.getItems().add(subMenu);
        menu.getItems().add(separator);
        menu.getItems().add(setting);
    }
}