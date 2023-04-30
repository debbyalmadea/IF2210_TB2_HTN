package com.htn.view;

import com.htn.view.customer.CustomerView;
import com.htn.view.main.MainView;
import javafx.scene.control.Tab;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ViewFactory {
    public static @Nullable View get(@NotNull String request, Tab parent) {
        if (request.equalsIgnoreCase("customer")) {
            View view = new CustomerView(parent);
            // TODO! SEARCH BETTER WAY TO BIND THE TAB TEXT
            parent.textProperty().bindBidirectional(view.getTitle());
            return view;
        } else if (request.equalsIgnoreCase("main")) {
            View view = new MainView(parent);
            parent.textProperty().bindBidirectional(view.getTitle());
            return view;
        }
        return null;
    }
}